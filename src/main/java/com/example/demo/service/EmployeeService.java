package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import com.example.demo.request.AddStaffRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service

public class EmployeeService {
    private final UserRepo userRepo;
    public List<UserDTO> getAllEmployees() {
        return userRepo.findAll().stream()
                .filter( ad ->
                        ad.getRole().equals("admin")&&ad.getStatus().equals("active")
                )
                .map( ct ->
                        UserDTO.builder()
                                .userId(ct.getUserId())
                                .full_name(ct.getFull_name())
                                .username(ct.getUsername())
                                .password(ct.getPassword())
                                .email(ct.getEmail())
                                .phone(ct.getPhone())
                                .createdAt(ct.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .updatedAt(ct.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .build()
                ).collect(Collectors.toList());
    }
    @Transactional
    public UserDTO addStaff(AddStaffRequest addStaffRequest) {

        UserEntity userEntity = userRepo.findByUsername(addStaffRequest.getUsername()).orElse(null);
        if(userEntity != null) {
            return null;
        }
        UserEntity getEmail = userRepo.findByEmail(addStaffRequest.getEmail()).orElse(null);
        if(getEmail != null) {
            return null;
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(addStaffRequest.getPassword());

        UserEntity staffSave = new UserEntity();

        staffSave.setUsername(addStaffRequest.getUsername());
        staffSave.setFull_name(addStaffRequest.getFull_name());
        staffSave.setPassword(encodedPassword);
        staffSave.setEmail(addStaffRequest.getEmail());
        staffSave.setPhone(addStaffRequest.getPhone());
        staffSave.setRole("admin");
        staffSave.setStatus("active");
        staffSave.setCreatedAt(LocalDateTime.now());

        UserEntity savedStaff;
        try {
            savedStaff = userRepo.save(staffSave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return UserDTO.builder()
                .full_name(savedStaff.getFull_name())
                .username(savedStaff.getUsername())
                .email(savedStaff.getEmail())
                .phone(savedStaff.getPhone())
                .build();

    }

}
