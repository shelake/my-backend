package com.example.product.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsDto2 {
    private int postId;
    private String content;
    private Timestamp timestamp;
    private int userid;
    private String profile_picture;
    private String username;
}
