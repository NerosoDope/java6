package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false, length = 255)
    private String address;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems;

    @OneToOne(mappedBy = "order")
    private PaymentEntity payment;
}
