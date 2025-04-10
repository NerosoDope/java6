package com.example.demo.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductRequest {

    private Integer productId;

    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private Integer categoryId;

    private MultipartFile imageUrl;


}
