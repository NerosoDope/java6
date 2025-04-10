package com.example.demo.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoanhThuDTO {
    private Integer productId;
    private String productName;
    private String productPrice;
    private Integer totalQuantity;
    private String totalPrice;
    private Integer totalCancel;
    private String createDate;
}
