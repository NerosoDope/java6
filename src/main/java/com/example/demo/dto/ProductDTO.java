package com.example.demo.dto;

import com.example.demo.entity.CategoryEntity;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer productId;
    private String name;
    private String description;
    private String price;
    private Integer stockQuantity;
    private CategoryEntity category;
    private String imageUrl;
    private String status;
    private String createdAt;
    private String updatedAt;

}
