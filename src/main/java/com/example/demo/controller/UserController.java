package com.example.demo.controller;

import com.example.demo.auth.SessionService;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import com.example.demo.request.ChangePWRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    SessionService sessionService;
    UserService userService;
    EmailService emailService;
    public static final String PHONE_PATTERN = "^(\\+48|0)(3[2-9]|5[6|8-9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$";
    public static final Pattern phone_pattern = Pattern.compile(PHONE_PATTERN);

    @PostMapping("/changePW")
    public String changePW(Model model,
                           HttpServletRequest request,
                           @ModelAttribute("changepw")ChangePWRequest changePWRequest) {
        HttpSession session = request.getSession();
        changePWRequest.setUsername((String) session.getAttribute("userName"));
        model.addAttribute("user", userService.getUserByUserName((String) session.getAttribute("userName")));
        model.addAttribute("changepw", new ChangePWRequest());
        if(!changePWRequest.getNewPassword().equals(changePWRequest.getNewPassword2())){
            model.addAttribute("message", "Mật khẩu không trùng khớp!");
            return"user/thongTinKhachHang";
        }

        UserDTO u = userService.changePassword(changePWRequest);

        if(u == null){
            model.addAttribute("message", "Mật khẩu cũ ko chính xác!");
            return"user/thongTinKhachHang";
        }
        model.addAttribute("message", "Đổi mật khẩu thành công!");
        return"user/thongTinKhachHang";
    }
    @PostMapping("/updateUser")
    public String updateUser(Model model,
                             HttpServletRequest request,
                             @ModelAttribute("changepw") RegisterRequest updateUserRequest
    ) {
        Matcher phone_matcher = phone_pattern.matcher(updateUserRequest.getPhone());
        HttpSession session = request.getSession();
        model.addAttribute("user", userService.getUserByUserName((String) session.getAttribute("userName")));
        model.addAttribute("changepw", new ChangePWRequest());
        if(!phone_matcher.matches()){
            model.addAttribute("message", "Vui lòng nhập đúng số điện thoại!");
            return"user/thongTinKhachHang";
        }
        UserDTO userResponse = userService.updateUser(updateUserRequest);
        if(userResponse == null) {
            model.addAttribute("message", "Vui lòng kiểm tra lại thông tin!");
            return"user/thongTinKhachHang";
        }

        model.addAttribute("message", "Cập nhật thành công!");
        return"user/thongTinKhachHang";
    }

}
