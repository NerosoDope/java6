package com.example.demo.controller;


import com.example.demo.request.AddCartRequest;
import com.example.demo.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/addCart")
    public String addCart(@ModelAttribute("addCartRequest") AddCartRequest addCartRequest,
                          @RequestParam("prdId") Integer prdId,
                          HttpServletRequest request,
                          Model model) {
        cartService.addCart(prdId, request);
        return "redirect:/cart";
    }

    @PostMapping("/deleteCartItem")
    public String deleteCartItem(@RequestParam("cartId") Integer cartId,
                          HttpServletRequest request,
                          Model model) {
        try {
            cartService.deleteCartItem(cartId);
        }catch (Exception e) {
            return "redirect:/cart";
        }
        return "redirect:/cart";
    }


}
