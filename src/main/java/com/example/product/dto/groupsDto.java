package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class groupsDto {
    private int groupid;
    private String groupname;
    private int adminid;
}
