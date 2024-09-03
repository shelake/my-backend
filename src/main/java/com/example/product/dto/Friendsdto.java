package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendsdto {
    private int friendshipid;
    private statustype status;
    private int userid1;
    private int userid2;
}
