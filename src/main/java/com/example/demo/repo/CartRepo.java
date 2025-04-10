package com.example.demo.repo;

import com.example.demo.entity.CartEntity;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Integer> {

}
