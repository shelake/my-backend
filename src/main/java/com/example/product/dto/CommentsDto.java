package com.example.product.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
    private int commentID;
    private String comment_text;
    private Timestamp timestamp;
    private int userid;
}