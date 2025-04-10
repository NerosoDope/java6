package com.example.demo.repo;


import com.example.demo.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<PaymentEntity, Integer> {

    @Query(value = "select *  from payment where order_id =:orderID", nativeQuery = true)
    Optional<PaymentEntity> findByOrder(int orderID);
}
