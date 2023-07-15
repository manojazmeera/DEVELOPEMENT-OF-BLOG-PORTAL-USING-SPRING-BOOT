package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    //http://localhost:8080/api/posts/2/comments
    @PostMapping("/posts/{postId}/comments")
   public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") long postId,
                                                   @RequestBody CommentDto commentDto ){
        CommentDto comment = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(comment,HttpStatus.CREATED);
   }

    //http://localhost:8080/api/posts/2/comments
   @GetMapping("/posts/{postId}/comments")
   public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
   }

    //http://localhost:8080/api/posts/2/comments/1
   @GetMapping("/posts/{postId}/comments/{commentId}")
   public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "commentId") Long commentId){
       CommentDto commentById = commentService.getCommentById(postId, commentId);
       return new ResponseEntity<>(commentById,HttpStatus.OK);

   }

    //http://localhost:8080/api/posts/2/comments/1
   @PutMapping("/posts/{postId}/comments/{commentId}")
   public ResponseEntity<CommentDto> updateComment(
           @PathVariable(value = "postId") Long postId,
           @PathVariable(value = "commentId") Long commentId,
           @RequestBody CommentDto commentDto
   ){
       CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
       return new ResponseEntity<>(dto,HttpStatus.OK) ;
   }

    //http://localhost:8080/api/posts/2/comments/1
   @DeleteMapping("/posts/{postId}/comments/{commentId}")
   public ResponseEntity<String> deleteComment(
           @PathVariable(value = "postId") Long postId,
           @PathVariable(value = "commentId") Long commentId
   ) {
       commentService.deleteComment(postId, commentId);
       return new ResponseEntity<>("Comment is Deleted", HttpStatus.OK);
   }
}
