package com.example.demo.auth;

import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(username)
                );
        System.out.println("User:" + user);
        List<GrantedAuthority> authorityList =
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        System.out.println("authorityList:"+authorityList);
        return new User(user.getUsername(), user.getPassword(), authorityList);
    }
}
