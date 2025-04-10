package com.example.demo.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddStaffRequest {

    private String username;

    private String full_name;

    private String password;

    private String email;

    private String phone;


}
