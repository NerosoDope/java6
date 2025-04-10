package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String username;
    private String full_name;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String createdAt;
    private String updatedAt;

}