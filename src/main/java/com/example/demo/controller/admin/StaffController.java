package com.example.demo.controller.admin;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.request.AddStaffRequest;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    public static final String PHONE_PATTERN = "^(\\+48|0)(3[2-9]|5[6|8-9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$";
    public static final Pattern phone_pattern = Pattern.compile(PHONE_PATTERN);

    private final EmployeeService employeeService;
    private final UserService userService;

    @PostMapping("/addStaff")
    public String addStaff(Model model,
                              @ModelAttribute("/newStaff") AddStaffRequest addStaffRequest
                              ) throws IllegalAccessException {


        Matcher phone_matcher = phone_pattern.matcher(addStaffRequest.getPhone());

        if(addStaffRequest.getFull_name().equals("")){
            return "redirect:/admin/employees?message=emptyFullName";
        }if(addStaffRequest.getUsername().equals("")){
            return "redirect:/admin/employees?message=emptyUserName";
        }if(addStaffRequest.getPassword().equals("")){
            return "redirect:/admin/employees?message=emptyPassword";
        }if(addStaffRequest.getEmail().equals("")){
            return "redirect:/admin/employees?message=emptyEmail";
        }if(addStaffRequest.getPhone().equals("")){
            return "redirect:/admin/employees?message=emptyPhone";
        }if(!phone_matcher.matches()){
            return "redirect:/admin/employees?message=failPhone";
        }
        UserDTO userDTO = employeeService.addStaff(addStaffRequest);
        if(userDTO == null) {
            return "redirect:/admin/employees?message=categoryExist";
        }else {
            return "redirect:/admin/employees?message=addAccess";
        }

    }
    @PostMapping("/deleteStaff")
    public String deleteStaff(@RequestParam("userID") Integer userID,
                                    Model model) {
        UserDTO user = userService.deleteUser(userID);
        if (user != null) {
            return "redirect:/admin/employees?message=deleteAccess";
        } else {
            return "redirect:/admin/employees?message=deleteFailed";
        }
    }
    @PostMapping("/deleteUser")
    public String updateOrderStatus(@RequestParam("userID") Integer userID,
                                    Model model) {
        UserDTO user = userService.deleteUser(userID);
        List<UserDTO> customerList = userService.getAllCustomers();
        model.addAttribute("customers", customerList);
        if (user != null) {
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa không thành công");
        }
        return "admin/quanLyKhachHang";
    }
}
