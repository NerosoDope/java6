package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.*;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductsRepo productsRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;
    private final PaymentRepo paymentRepo;


    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(od ->
                        OrderDTO.builder()
                                .orderId(od.getOrderId())
                                .userId(od.getUser().getUserId())
                                .fullName(od.getUser().getFull_name())
                                .sdt(od.getUser().getPhone())
                                .orderDate(od.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(od.getTotalPrice()))
                                .address(od.getAddress())
                                .status(od.getStatus())
                                .orderItems(od.getOrderItems())
                                .payment(od.getPayment())
                                .build()
                ).collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersForUser(Integer userId) {
        return orderRepo.findByUsedId(userId).stream()
                .map(od ->
                        OrderDTO.builder()
                                .orderId(od.getOrderId())
                                .orderDate(od.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(od.getTotalPrice()))
                                .address(od.getAddress())
                                .status(od.getStatus())
                                .orderItems(od.getOrderItems())
                                .payment(od.getPayment())
                                .build()
                ).collect(Collectors.toList());
    }

    public OrderDTO getAllOrderById(Integer orderID) {
        OrderEntity od = orderRepo.findById(orderID).get();
        return OrderDTO.builder()
                                .orderId(od.getOrderId())
                                .orderDate(od.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(od.getTotalPrice()))
                                .address(od.getAddress())
                                .status(od.getStatus())
                                .orderItems(od.getOrderItems())
                                .payment(od.getPayment())
                                .build();
    }

    public OrderDTO order(Map<Integer, Integer> map, String address, String userId, String paymentType) {
        List<OrderItemEntity> orderItemList = new ArrayList<>();
        UserEntity user = userRepo.findByUsername(userId).get();
        double totalPrice = 0;


        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ProductsEntity productsEntity = productsRepo.findById(entry.getKey()).get();
            OrderItemEntity orderItemEntity = new OrderItemEntity();

            orderItemEntity.setQuantity(entry.getValue());
            orderItemEntity.setProduct(productsEntity);
            orderItemEntity.setPrice(productsEntity.getPrice());
            orderItemList.add(orderItemEntity);
            totalPrice += productsEntity.getPrice() * entry.getValue();
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setUpdatedAt(LocalDateTime.now());
        orderEntity.setAddress(address);
        orderEntity.setStatus("DANG_GIAO");
        orderEntity.setUser(user);
        orderEntity.setTotalPrice(totalPrice);

        OrderEntity savedOrderEntity;
        OrderEntity saved;
        PaymentEntity payment = new PaymentEntity();
        payment.setStatus("CHUA_THANH_TOAN");

        if (paymentType.equals("ONLINE")) {
            payment.setType("ONLINE");
        } else {
            payment.setType("OFFLINE");
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        payment.setContentPayment("SO"+sb.toString());
        try {
            saved = orderRepo.save(orderEntity);
            OrderEntity setOrderEntity = orderRepo.findById(saved.getOrderId()).get();
            for (OrderItemEntity orderItemEntity : orderItemList) {
                orderItemEntity.setOrder(setOrderEntity);
                orderItemRepo.save(orderItemEntity);
            }
            payment.setOrder(saved);
            paymentRepo.save(payment);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        savedOrderEntity = orderRepo.findById(saved.getOrderId()).get();

        return OrderDTO.builder()
                .orderDate(savedOrderEntity.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(savedOrderEntity.getTotalPrice()))
                .address(savedOrderEntity.getAddress())
                .status(savedOrderEntity.getStatus())
                .build();
    }

    public OrderDTO updateOrderStatus(Integer orderId, String status) {
        OrderEntity order = orderRepo.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        PaymentEntity payment = paymentRepo.findByOrder(orderId).get();

        if (status.equals("DA_NHAN") && !order.getStatus().equals("DA_NHAN")) {
            for (OrderItemEntity orderItemEntity : order.getOrderItems()) {
                ProductsEntity pro = orderItemEntity.getProduct();
                pro.setStockQuantity(pro.getStockQuantity() - orderItemEntity.getQuantity());
                orderItemEntity.setProduct(pro);
                productsRepo.save(pro);
            }
            payment.setStatus("DA_THANH_TOAN");
        }

        if (status.equals("TRA_HANG") && !order.getStatus().equals("TRA_HANG")) {
            for (OrderItemEntity orderItemEntity : order.getOrderItems()) {
                ProductsEntity pro = orderItemEntity.getProduct();
                pro.setStockQuantity(pro.getStockQuantity() + orderItemEntity.getQuantity());
                orderItemEntity.setProduct(pro);
                productsRepo.save(pro);
            }
            payment.setStatus("CHUA_THANH_TOAN");
        }
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        OrderEntity savedOrderEntity;
        try {
            savedOrderEntity = orderRepo.save(order);
            paymentRepo.save(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return OrderDTO.builder()
                .orderId(savedOrderEntity.getOrderId())
                .orderDate(savedOrderEntity.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(savedOrderEntity.getTotalPrice()))
                .address(savedOrderEntity.getAddress())
                .status(savedOrderEntity.getStatus())
                .orderItems(savedOrderEntity.getOrderItems())
                .build();
    }
    public OrderDTO updatePayment(Integer orderId) {
        OrderEntity order = orderRepo.findById(orderId).orElse(null);
        PaymentEntity payment = paymentRepo.findByOrder(orderId).get();
        if (order == null || payment == null) {
            return null;
        }
        payment.setStatus("DA_THANH_TOAN");
        try {
            paymentRepo.save(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(order.getTotalPrice()))
                .address(order.getAddress())
                .status(order.getStatus())
                .orderItems(order.getOrderItems())
                .build();
    }
}
