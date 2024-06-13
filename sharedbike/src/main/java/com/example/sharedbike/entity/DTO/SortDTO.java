package com.example.sharedbike.entity.DTO;

import lombok.Data;

@Data
public class SortDTO {

   private int page;
    private int size;
    private String sortBy;
    private String sortOrder;
}
