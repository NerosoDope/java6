package com.example.demo.controller.admin;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class OrdersController {

    private final OrderService orderService;

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId,
                                    @RequestParam("status") String status,
                                    Model model) {
        OrderDTO order = orderService.updateOrderStatus(orderId, status);

        if (order != null) {
            model.addAttribute("message", "Cập nhật trạng thái thành công!");
        } else {
            model.addAttribute("message", "Cập nhật trạng thái thất bại!");
        }
        List<OrderDTO> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);
        return "admin/quanLyDonHang";
    }

    @PostMapping("/updatePayment")
    public String updatePayment(@RequestParam("orderId") Integer orderId,
                                Model model) {
        OrderDTO order = orderService.updatePayment(orderId);

        if (order != null) {
            model.addAttribute("message", "Cập nhật trạng thái thành công!");
        } else {
            model.addAttribute("message", "Cập nhật trạng thái thất bại!");
        }
        List<OrderDTO> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);
        return "admin/quanLyThanhToan";
    }


}
