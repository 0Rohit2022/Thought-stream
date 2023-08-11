package com.blog.service.Impl;

import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.postResponse;
import com.blog.repositories.Postrepositories;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private Postrepositories postrepositories;
    private ModelMapper mapper;

    public PostServiceImpl(Postrepositories postrepositories, ModelMapper mapper) {
        this.postrepositories = postrepositories;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post savedPost = postrepositories.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public postResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

       Sort sort =  sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> content = postrepositories.findAll(pageable);
        List<Post> posts = content.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        postResponse PostResponse = new postResponse();
        PostResponse.setContent(dtos);
        PostResponse.setPageNo(content.getNumber());
        PostResponse.setPageSize(content.getSize());
        PostResponse.setTotalPages(content.getTotalPages());
        PostResponse.setTotalElements(content.getTotalElements());
        PostResponse.setLast(content.isLast());

        return PostResponse;
    }

    @Override
    public PostDto getPostById(long id) {
       Post post =  postrepositories.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with id: " +id)
        );
      PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postrepositories.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found id: " + id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postrepositories.save(post);
        PostDto dto =mapToDto(updatedPost);

        return dto;

    }

    @Override
    public void deletePost(long id) {
        Post post = postrepositories.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found id: " + id)
        );
        postrepositories.deleteById(id);
    }

    Post mapToEntity(PostDto postDto)
    {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }

    PostDto mapToDto(Post post)
    {
        PostDto dto = mapper.map(post , PostDto.class);
        return dto;
    }

}
