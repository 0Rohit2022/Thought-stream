package com.blog.service;

import com.blog.payload.PostDto;
import com.blog.payload.postResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    postResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);
}
