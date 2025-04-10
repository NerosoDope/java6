package com.example.demo.service;

import com.example.demo.dto.CartDTO;
import com.example.demo.entity.CartEntity;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.CartItemRepo;
import com.example.demo.repo.CartRepo;
import com.example.demo.repo.ProductsRepo;
import com.example.demo.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final UserRepo userRepo;
    private final ProductsRepo productsRepo;

    public List<CartDTO> getCartsForUser(HttpServletRequest request) {
        List<CartEntity> carts = new ArrayList<>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("userName");
        carts = userRepo.findByUsername(username).get().getCarts();

        return carts.stream()
                .map(gc ->
                        CartDTO.builder()
                                .cartId(gc.getCartId())
                                .productId(gc.getCartItems().get(0).getProduct().getProductId())
                                .productName(gc.getCartItems().get(0).getProduct().getName())
                                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(gc.getCartItems().get(0).getProduct().getPrice()))
                                .stockQuantity(gc.getCartItems().get(0).getProduct().getStockQuantity())
                                .createdAt(gc.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .updatedAt(gc.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .img(gc.getCartItems().get(0).getProduct().getImageUrl())
                                .build()
                        ).collect(Collectors.toList());
    }

    public void addCart(Integer productId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        UserEntity user = userRepo.findByUsername(userName).orElse(null);
        ProductsEntity prd = productsRepo.findById(productId).orElse(null);

        if (user == null || prd == null) {
            throw new RuntimeException("Not found");
        }

        CartEntity cart = new CartEntity();
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());

        CartEntity cartSaved ;

        try {
            cartSaved = cartRepo.save(cart);

            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setProduct(prd);
            cartItem.setQuantity(1);
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setCart(cartSaved);

            cartItemRepo.save(cartItem);
        }catch (Exception e){
            throw new RuntimeException("Not found");
        }
    }
    public void deleteCartItem(Integer cartId) {
        CartEntity cart = cartRepo.findById(cartId).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Not found");
        }
        try {
            cartItemRepo.delete(cart.getCartItems().get(0));
            cartRepo.delete(cart);
        }catch (Exception e){
            throw new RuntimeException("Not found");
        }
    }
}
