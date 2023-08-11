package com.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {
    public static void main(String[] args)
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.print(encoder.encode("testing"));
    }
}
