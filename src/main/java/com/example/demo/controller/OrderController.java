package com.example.demo.controller;


import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.rmi.server.LogStream.log;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping("/order")
    public String checkout(@RequestParam("productIds[]") Integer[] productIds,
                           @RequestParam("quantities[]") Integer[] quantities,
                           @RequestParam("address") String address,
                           @RequestParam("paymentType") String paymentType,
                           HttpServletRequest request,
                           Model model) {
        if(address == null || address.isEmpty() || address.equals("")) {
            return "redirect:/cart?message=error";
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            Integer productId = productIds[i];
            System.out.println("productId: "+productId);
            Integer quantity = quantities[i];
            map.put(productId, quantity);
        }
        HttpSession session = request.getSession();
        orderService.order(map, address, (String) session.getAttribute("userName"), paymentType);

        return "redirect:/orderHistory";
    }

    @GetMapping("/orderHistory")
    public String orderHistory(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("orderList", orderService.getAllOrdersForUser(userId));
        return "user/orderHistory";
    }

    @PostMapping("/updateOrderUser")
    public String updateOrderStatus(@RequestParam("orderId") Integer orderId,
                                    @RequestParam("status") String status,
                                    HttpServletRequest request,
                                    Model model) {
        OrderDTO order = orderService.updateOrderStatus(orderId, status);

        if (order != null) {
            model.addAttribute("message", "Cập nhật trạng thái thành công!");
        } else {
            model.addAttribute("message", "Cập nhật trạng thái thất bại!");
        }
        model.addAttribute("message", "Cập nhật đơn \""+ orderId+"\" thành đã nhận hàng!");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("orderList", orderService.getAllOrdersForUser(userId));

        return "user/orderHistory";
    }

}
