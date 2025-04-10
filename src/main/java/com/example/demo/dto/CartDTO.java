package com.example.demo.dto;

import com.example.demo.entity.CartItemEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Integer cartId;
    private String createdAt;
    private String updatedAt;
    private Integer productId;
    private String productName;
    private String price;
    private Integer stockQuantity;
    private String img;
}
