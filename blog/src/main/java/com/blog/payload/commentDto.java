package com.blog.payload;

import lombok.Data;

@Data
public class commentDto {
    private long id;
    private String name;
    private String email;
    private String body;

}
