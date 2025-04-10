package com.example.demo.controller.admin;


import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.request.AddProductRequest;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final CategoryRepo categoryRepo;

    @PostMapping("/addProduct")
    public String addProduct(Model model,
                             @ModelAttribute("newProduct")AddProductRequest addProductRequest,
                             @RequestParam("imageUrl") MultipartFile imageUrl
                             ) throws IllegalAccessException {
        if(addProductRequest.getName().equals("")){
            return "redirect:/admin/products?message=emptyName";
        }if(addProductRequest.getPrice() <= 0){
            return "redirect:/admin/products?message=PriceNull";
        }if(addProductRequest.getDescription().equals("")){
            return "redirect:/admin/products?message=emptyDescription";
        }if(addProductRequest.getStockQuantity() <= 0){
            return "redirect:/admin/products?message=stockQuantityNull";
        }
        String fileName = "";
        if (!imageUrl.isEmpty()) {
            String projectPath = System.getProperty("user.dir");
            String uploadDir = projectPath + "\\src\\main\\resources\\static\\img";
            fileName = imageUrl.getOriginalFilename();
            log.info(fileName);

            try {
                imageUrl.transferTo(new File(uploadDir + File.separator + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ProductDTO productDTO = productService.addProduct(addProductRequest, fileName);

        if(productDTO == null) {
            return "redirect:/admin/products?message=productExist";
        }else{
            return "redirect:/admin/products?message=addAccess";
        }

    }
    @PostMapping("/dropProduct")
    public String updateOrderStatus(@RequestParam("prdID") Integer prdID,
                                    Model model) {
        ProductDTO productDTO = productService.deleteProduct(prdID);

        if (productDTO != null) {
            return "redirect:/admin/products?message=dropAccess";
        } else {
            return "redirect:/admin/products?message=dropFailed";
        }
    }

    @GetMapping("/searchForUpdate")
    public String searchForUpdate(@RequestParam("prdID") Integer prdID,
                                    Model model) {
        AddProductRequest productDTO = productService.findForUpdate(prdID);
        System.out.println("ID sản phâmr :"+prdID);
        List<ProductDTO> productList = productService.getAllForAdmin();
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("products", productList);
        if (productDTO != null) {
            model.addAttribute("newProduct", productDTO);
            model.addAttribute("edit", true);
            return "admin/quanLySanPham";
        } else {
            model.addAttribute("newProduct", new AddProductRequest());
            model.addAttribute("message", "Không tìm thấy sản phẩm");
            return "admin/quanLySanPham";
        }
    }
    @PostMapping("/updateProduct")
    public String updateProduct(Model model,
                                @ModelAttribute("newProduct")AddProductRequest addProductRequest,
                                @RequestParam("imageUrl") MultipartFile imageUrl
    ) {
        if(addProductRequest.getName().equals("")){
            return "redirect:/admin/products?message=emptyName";
        }if(addProductRequest.getPrice() <= 0){
            return "redirect:/admin/products?message=PriceNull";
        }if(addProductRequest.getDescription().equals("")){
            return "redirect:/admin/products?message=emptyDescription";
        }if(addProductRequest.getStockQuantity() <= 0){
            return "redirect:/admin/products?message=stockQuantityNull";
        }
        if(imageUrl.isEmpty()){
            return "redirect:/admin/products?message=UpdateFailed";
        }
        String fileName = "";
        if (!imageUrl.isEmpty()) {
            String projectPath = System.getProperty("user.dir");
            String uploadDir = projectPath + "\\src\\main\\resources\\static\\img";
            fileName = imageUrl.getOriginalFilename();
            log.info(fileName);

            try {
                imageUrl.transferTo(new File(uploadDir + File.separator + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ProductDTO productDTO = productService.updateProduct(addProductRequest, fileName);

        if(productDTO == null) {
            return "redirect:/admin/products?message=UpdateFailed";
        }else{
            return "redirect:/admin/products?message=UpdateAccess";
        }
    }




}
