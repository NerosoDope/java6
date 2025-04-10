package com.example.demo.repo;

import com.example.demo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "select * from Orders where user_id = :userId", nativeQuery = true)
    List<OrderEntity> findByUsedId(Integer userId);

    @Query(value = "SELECT count(o.user_id) from Orders o ;", nativeQuery = true)
    Long getSoKH();

    @Query(value = "SELECT sum(o.total_price) from Orders o where o.status like 'DA_NHAN';", nativeQuery = true)
    Long getTongDoanhThu();

    @Query(value = "SELECT count(o.order_id) from Orders o", nativeQuery = true)
    Long getSoDonHang();
}
