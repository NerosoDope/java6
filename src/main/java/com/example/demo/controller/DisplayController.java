package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.request.AddCartRequest;
import com.example.demo.request.ChangePWRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Controller
@RequiredArgsConstructor
public class DisplayController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("Iphone", productService.getAll().stream()
                .filter(p -> p.getCategory().getName().equals("Iphone")).limit(4)
                .collect(Collectors.toList()));
        model.addAttribute("Oppo", productService.getAll().stream()
                .filter(p -> p.getCategory().getName().equals("Oppo")).limit(4)
                .collect(Collectors.toList()));
        model.addAttribute("Samsung", productService.getAll().stream()
                .filter(p -> p.getCategory().getName().equals("Samsung")).limit(4)
                .collect(Collectors.toList()));
        return "/index";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request, @RequestParam(defaultValue = "") String message) {
        model.addAttribute("cart", cartService.getCartsForUser(request));
        if(message != null && !message.equals("")) {
            model.addAttribute("message", "Vui lòng nhập địa chỉ");
        }
        return "user/cart";
    }
    @GetMapping("/payment")
    public String delivery(Model model,
                           @RequestParam("orderID") Integer orderID) {
        model.addAttribute("orderList", orderService.getAllOrderById(orderID));
        return "user/payment";
    }

    @GetMapping("/product")
    public String product(Model model, @RequestParam int id) {
        ProductDTO prd = productService.getProductById(id);
        model.addAttribute("product", prd);
        model.addAttribute("addCartRequest", new AddCartRequest());
        return "user/product";
    }

    @GetMapping("/shop")
    public String shop(Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "catelo", defaultValue = "") String catelo) {
        List<ProductDTO> products = null;
        if (catelo != null && !catelo.equals("")) {
            products =  productService.getAll()
                    .stream().filter(p -> p.getCategory().getName().toLowerCase().equals(catelo.toLowerCase())).collect(Collectors.toList());
        }else{
            products =  productService.getAllByPagination(page, 8);
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAll());
        int pageNumbers = (int) Math.ceil(productService.getAll().size() / 8);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("page", page);
        return "user/category";
    }

    @GetMapping("/dangKi")
    public String category(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "user/signup";
    }

    @GetMapping("/thongTinKhachHang")
    public String thongTinKhachHang(Model model,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("user", userService.getUserByUserName((String) session.getAttribute("userName")));
        model.addAttribute("changepw", new ChangePWRequest());
        return "user/thongTinKhachHang";
    }

}
