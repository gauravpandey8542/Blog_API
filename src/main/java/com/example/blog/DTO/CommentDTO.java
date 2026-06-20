package com.example.blog.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;   // just the id
    private Long userId;
}
