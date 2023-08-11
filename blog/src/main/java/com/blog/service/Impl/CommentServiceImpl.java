package com.blog.service.Impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.commentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.Postrepositories;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private Postrepositories postrepositories;
    private CommentRepository commentRepository;

   private ModelMapper mapper;



    public CommentServiceImpl(CommentRepository commentRepository,Postrepositories postrepositories,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postrepositories = postrepositories;
        this.mapper = mapper;
    }

    @Override
    public commentDto saveComment(Long postId, commentDto commentdto) {
       Post post = postrepositories.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with id: " + postId)
        );
       Comment comment = mapToEntity(commentdto);
       comment.setPost(post);
       Comment newComment = commentRepository.save(comment);

       commentDto dto = mapToDto(newComment);


        return dto;
    }

    @Override
    public List<commentDto> getCommentByPostId(long postId) {

       List<Comment> comments = commentRepository.findByPostId(postId);
      return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public commentDto getCommentById(long postId, long commentId) {
        Post post = postrepositories.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not found with id: " + commentId)
        );

        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException("Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public commentDto updateComment(long postId, long commentId, commentDto commentdto) {
        Post post = postrepositories.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not found with id: " + commentId)
        );

        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException("Comment does not belong to post");
        }

        comment.setName(commentdto.getName());
        comment.setEmail(commentdto.getEmail());
        comment.setBody(commentdto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postrepositories.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not found with id: " + commentId)
        );
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException("Comment does not belong to post");
        }
         commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(commentDto commentdto)
    {
        Comment comment = mapper.map(commentdto, Comment.class);

        return comment;
    }

    commentDto mapToDto(Comment comment)
    {
        commentDto dto = mapper.map(comment, commentDto.class);

        return dto;
    }
}
