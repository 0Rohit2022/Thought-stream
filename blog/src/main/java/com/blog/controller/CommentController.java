package com.blog.controller;

import com.blog.payload.commentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/")
@RestController
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

// http://localhost:3030/api/{postid}/comments

        @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<commentDto> createComment(@PathVariable("postId") long postId,
                                                    @RequestBody commentDto commentdto)
        {
            commentDto dto = commentService.saveComment(postId, commentdto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
        @GetMapping("/posts/{postId}/comments")
    public List<commentDto> getCommentByPostId(@PathVariable("postId") long postId)
        {
           List<commentDto> dto =  commentService.getCommentByPostId((postId));
           return dto;
        }

//        http://localhost:3030/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<commentDto> getCommentById(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId)
    {
        commentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    //        http://localhost:3030/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<commentDto> updateComment(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId, @RequestBody commentDto commentdto)
    {
       commentDto dto = commentService.updateComment(postId, commentId, commentdto);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
    //        http://localhost:3030/api/posts/1/comments/1
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId)
    {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment is deleted", HttpStatus.OK);
    }


}
