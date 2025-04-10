package com.example.demo.repo;

import com.example.demo.dto.DoanhThuDTO;
import com.example.demo.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepo extends JpaRepository<ProductsEntity, Integer> {

    Optional<ProductsEntity> findByName(String name);

    @Query(value = """
                 SELECT
                    p.product_id AS productId,
                    p.name AS name,
                    p.price AS price,
                    ( SELECT sum(oi3.quantity)
                		FROM Order_Items oi3
                		INNER JOIN Products p3  ON p3.product_id = oi3.product_id
                		INNER JOIN Orders o3 ON o3.order_id = oi3.order_id
                		where o3.status like 'DA_NHAN' AND p3.product_id = p.product_id
                	) AS totalQuantity,
                    (SELECT (sum(oi2.quantity) * p.price)
                		FROM Order_Items oi2
                		INNER JOIN Products p2  ON p2.product_id = oi2.product_id
                		INNER JOIN Orders o2 ON o2.order_id = oi2.order_id
                		where o2.status like 'DA_NHAN' AND p2.product_id = p.product_id
                	) AS totalOrderPrice
                FROM Products p
                INNER JOIN Order_Items oi ON p.product_id = oi.product_id
                INNER JOIN Orders o ON o.order_id = oi.order_id
                where o.status like 'DA_NHAN'
                GROUP BY p.product_id, p.name, p.price
                ORDER BY p.product_id ASC;
            """, nativeQuery = true)
    List<Object[]> getRevenue();

    @Query(value = "SELECT sum(oi.quantity) FROM Products p\n" +
            "INNER JOIN Order_Items oi ON p.product_id = oi.product_id\n" +
            "INNER JOIN Orders o ON o.order_id = oi.order_id\n" +
            "where o.status like 'DA_NHAN';", nativeQuery = true)
    Long getSanPhamBanDuoc();

    @Query(value = """
                SELECT\s
                  p.product_id AS productId,
                  p.name AS name,
                  p.price AS price,
                  ( SELECT sum(oi3.quantity)
                    FROM Order_Items oi3
                    INNER JOIN Products p3  ON p3.product_id = oi3.product_id
                    INNER JOIN Orders o3 ON o3.order_id = oi3.order_id
                    where o3.status like 'DA_NHAN'AND p3.product_id = p.product_id
                ) AS totalQuantity,
                  ( SELECT sum(oi2.quantity)
                    FROM Products p2
                    INNER JOIN Order_Items oi2 ON p2.product_id = oi2.product_id
                    INNER JOIN Orders o2 ON o2.order_id = oi2.order_id
                    where o2.status like 'TRA_HANG'AND p2.product_id = p.product_id
                ) AS totalCancel,
                
                p.created_at AS ngayMoBan
              FROM Products p
              INNER JOIN Order_Items oi ON p.product_id = oi.product_id
              INNER JOIN Orders o ON o.order_id = oi.order_id
              group by p.product_id ,p.name ,p.price, p.created_at
              ORDER BY p.product_id ASC;
            """, nativeQuery = true)
    List<Object[]> getThongKeSanPham();

    @Query(value = """
                SELECT TOP 1
                    p.name AS name
                FROM Products p
                INNER JOIN Order_Items oi ON p.product_id = oi.product_id
                INNER JOIN Orders o ON o.order_id = oi.order_id
                ORDER BY (SELECT sum(p3.product_id)
                		FROM Order_Items oi3
                		INNER JOIN Products p3  ON p3.product_id = oi3.product_id
                		INNER JOIN Orders o3 ON o3.order_id = oi3.order_id
                		where o3.status like 'DA_NHAN'AND p3.product_id = p.product_id) DESC;
                
            """, nativeQuery = true)
    String getMaxLuotBan();

    @Query(value = """
                SELECT count(o.order_id) from Orders o where o.status like 'TRA_HANG';
            """, nativeQuery = true)
    Long getSoDonBiHuy();
}


