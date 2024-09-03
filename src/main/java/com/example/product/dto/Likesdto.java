package com.example.product.dto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Likesdto {
    private int likeid;
    private Timestamp timestamp;
    private int userid;
}
