package com.example.demo.controller;

import com.example.demo.auth.SessionService;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.UserRepo;
import com.example.demo.request.LoginRequest;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {

    AuthenticationManager authenticationManager;
    SessionService sessionService;
    UserRepo userRepo;
    UserService userService;
    EmailService emailService;

    private final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final Pattern email_pattern = Pattern.compile(EMAIL_PATTERN);
    public static final String PHONE_PATTERN = "^(\\+48|0)(3[2-9]|5[6|8-9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$";
    public static final Pattern phone_pattern = Pattern.compile(PHONE_PATTERN);
    private static final String NAME_PATTERN = "^[\\p{L}\\s]+$";
    private static final Pattern name_pattern = Pattern.compile(NAME_PATTERN);
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    @GetMapping("/dangNhap")
    public String dangNhap(Model model, @RequestParam("error") Optional<String> error) {
        if(error.isPresent()) {
            model.addAttribute("message", "Vui lòng đăng nhập!");
        }
        model.addAttribute("userRequest", new LoginRequest());
        return "user/logIn";
    }

    @PostMapping("/checkLogin")
    public String login(Model model,
                        HttpServletRequest request,
                        @ModelAttribute("userRequest") LoginRequest loginRequest
    ) {

        try {
            UserEntity user = userRepo.findByUsername(loginRequest.getUsername())
                    .orElseThrow(()->
                            new UsernameNotFoundException(loginRequest.getUsername())
                    );
            log.info(user.getStatus());
            if (user.getStatus().equals("notActive")) {
                model.addAttribute("userRequest", new LoginRequest());
                model.addAttribute("message", "Tên người dùng hoặc mật khẩu không chính xác");
                return "user/logIn";
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            sessionService.saveUserToSession(request, authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("fullname", user.getFull_name());
            session.setAttribute("userId", user.getUserId());

            System.out.println("Current Authentication after setting: " + SecurityContextHolder.getContext());
            System.out.println(authentication.isAuthenticated());

            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                return "redirect:/admin/";
            }else{
                return "redirect:/";
            }
        } catch (
                Exception e) {
            log.info("Login failed!");
            model.addAttribute("userRequest", new LoginRequest());
            model.addAttribute("message", "Tên người dùng hoặc mật khẩu không chính xác");
            return "user/logIn";
        }

    }

    @GetMapping("/dangXuat")
    public String dangXuat(Model model, HttpServletRequest request) {
        sessionService.clearSession(request);
        return "view/dangNhap";
    }

    @GetMapping("/error/403")
    public String error403() {
        return "view/error";
    }

    @PostMapping("/sendOTP")
    public String sendOTP(Model model,
                          HttpServletRequest request,
                          @ModelAttribute("registerRequest")RegisterRequest registerRequest) {
        UserDTO user = userService.getUserByUserName(registerRequest.getUsername());
        Matcher phone_matcher = phone_pattern.matcher(registerRequest.getPhone());
        if(!registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            model.addAttribute("message", "Mật khẩu không trùng khớp!");
            model.addAttribute("registerRequest", new RegisterRequest());
            return "user/signup";
        }
        if(!phone_matcher.matches()){
            model.addAttribute("message", "Số điện thoại không đúng định dạng!");
            model.addAttribute("registerRequest", new RegisterRequest());
            return "user/signup";
        }
        if(user!= null) {
            model.addAttribute("message", "Tên đăng nhập đã tồn tại!");
            model.addAttribute("registerRequest", new RegisterRequest());
            return "user/signup";
        }

        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000));
        emailService.sendOTPtoUser(registerRequest.getEmail(), otp);
        HttpSession ss = request.getSession(true);
        ss.setAttribute("otp", otp);
        System.out.println(otp);
        model.addAttribute("userRequest", registerRequest);
        return "user/submitOTP";

    }

    @PostMapping("/submitRegister")
    public String submitRegister(Model model,
                          HttpServletRequest request,
                          @ModelAttribute("registerRequest")RegisterRequest registerRequest) {
        HttpSession ss = request.getSession(true);
        String otp = (String) ss.getAttribute("otp");
        if(!registerRequest.getOTP().equals(otp)){
            model.addAttribute("message", "Mã OTP không chính xác!");
            model.addAttribute("registerRequest", registerRequest);
            return "user/submitOTP";
        }
        UserDTO user = userService.register(registerRequest);
        if(user == null) {
            model.addAttribute("message", "Đăng kí không thành công!");
            model.addAttribute("registerRequest", registerRequest);
            return "user/signup";
        }
        model.addAttribute("message", "Đăng kí thành công!");
        model.addAttribute("userRequest", new LoginRequest());
        return "user/logIn";
    }
}




