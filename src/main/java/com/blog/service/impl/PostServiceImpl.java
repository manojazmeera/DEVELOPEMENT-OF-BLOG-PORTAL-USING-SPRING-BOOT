package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;


import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper
    ) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to Entity
        Post post = mapToEntity(postDto);

       // post.setId(postDto.getId());
      //  post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());

        //data saved
        Post newPost = postRepository.save(post);

        //Response back to Postman
        PostDto dto = mapToDto(newPost);

       // dto.setId(newPost.getId());
       // dto.setTitle(newPost.getTitle());
      //  dto.setDescription(newPost.getDescription());
      //  dto.setContent(newPost.getContent());

        return dto;
    }

    @Override
    public List<PostDto> ListAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

      //Ternory Operator

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> listOfPosts = postRepository.findAll(pageable);

        List<Post> posts = listOfPosts.getContent();

        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+id)
         );

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+id)
        );
        Post newPost = mapToEntity(postDto);
        newPost.setId(id);
        Post UpdatedPost = postRepository.save(newPost);

        PostDto dto = mapToDto(UpdatedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+id)
        );
        postRepository.deleteById(id);


    }

/// convert entity to dto
    PostDto mapToDto(Post post){

        PostDto dto = modelMapper.map(post, PostDto.class);

       /* PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());*/

        return dto;

    }
    Post mapToEntity(PostDto postDto) {

        Post post = modelMapper.map(postDto, Post.class);

       /* Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        return post;
    }


    }
