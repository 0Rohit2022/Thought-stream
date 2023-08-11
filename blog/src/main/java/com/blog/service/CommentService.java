package com.blog.service;

import com.blog.payload.commentDto;

import java.util.List;

public interface CommentService {
    public commentDto saveComment(Long postId, commentDto commentdto);
    List<commentDto> getCommentByPostId(long postId);
    commentDto getCommentById(long postId, long commentId);

    commentDto updateComment(long postId, long commentId, commentDto commentdto);

    void deleteComment(long postId, long commentId);
}
