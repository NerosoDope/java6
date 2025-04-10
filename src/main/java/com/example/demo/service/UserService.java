package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import com.example.demo.request.ChangePWRequest;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public List<UserDTO> getAllCustomers() {
        return userRepo.findAll().stream()
                .filter(us ->
                        us.getRole().equals("user") && us.getStatus().equals("active")
                )
                .map(ct ->
                        UserDTO.builder()
                                .userId(ct.getUserId())
                                .username(ct.getUsername())
                                .full_name(ct.getFull_name())
                                .email(ct.getEmail())
                                .phone(ct.getPhone())
                                .createdAt(ct.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .updatedAt(ct.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .build()
                ).collect(Collectors.toList());
    }

    public UserDTO getUserByUserName(String username) {
        UserEntity user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .updatedAt(user.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    public UserDTO register(RegisterRequest registerRequest) {
        UserEntity user = userRepo.findByUsername(registerRequest.getUsername()).orElse(null);
        if (user != null) {
            return null;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequest.getUsername());
        userEntity.setFull_name(registerRequest.getFull_name());
        userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userEntity.setEmail(registerRequest.getEmail());
        userEntity.setPhone(registerRequest.getPhone());
        userEntity.setStatus("active");
        userEntity.setRole("user");
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        try {
            userRepo.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return UserDTO.builder()
                .username(userEntity.getUsername())
                .full_name(userEntity.getFull_name())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .createdAt(userEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .updatedAt(userEntity.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }

    public UserDTO changePassword(ChangePWRequest changePasswordRequest) {
        UserEntity user = userRepo.findByUsername(
                changePasswordRequest.getUsername()).orElseThrow(()
                -> new RuntimeException("User Not Found"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())
                && changePasswordRequest.getNewPassword().equals(changePasswordRequest.getNewPassword2())) {
            changePasswordRequest.setNewPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            user.setPassword(changePasswordRequest.getNewPassword());
            user.setUpdatedAt(LocalDateTime.now());

            try {
                userRepo.save(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return UserDTO.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .full_name(user.getFull_name())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .createdAt(user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .updatedAt(user.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .build();
        } else {
            return null;
        }
    }

    public UserDTO updateUser(RegisterRequest updateUserRequest) {
        UserEntity user = userRepo
                .findByUsername(
                        updateUserRequest.getUsername())
                .orElseThrow();

        user.setFull_name(updateUserRequest.getFull_name());
        user.setEmail(updateUserRequest.getEmail());
        user.setPhone(updateUserRequest.getPhone());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        try {
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .full_name(user.getFull_name())
                .phone(user.getPhone())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().format(dateTimeFormatter))
                .updatedAt(user.getUpdatedAt().format(dateTimeFormatter))
                .build();
    }

    public UserDTO deleteUser(Integer userID) {
        UserEntity user = userRepo
                .findById(userID)
                .orElseThrow();
        if(user.getUsername().equals("admin1")) {
            return null;
        }
        user.setStatus("notActive");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        try {
            userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .full_name(user.getFull_name())
                .phone(user.getPhone())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().format(dateTimeFormatter))
                .updatedAt(user.getUpdatedAt().format(dateTimeFormatter))
                .build();
    }

}
