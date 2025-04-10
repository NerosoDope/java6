package com.example.demo.dto;

import com.example.demo.entity.OrderItemEntity;
import com.example.demo.entity.PaymentEntity;
import com.example.demo.entity.ProductsEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private String fullName;
    private String sdt;
    private String product;
    private Integer quantity;
    private Integer userId;
    private String orderDate;
    private String status;
    private String totalPrice;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PaymentEntity payment;
    List<OrderItemEntity> orderItems;
}
