package com.example.demo.controller.admin;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.request.AddCategoryRequest;
import com.example.demo.request.AddProductRequest;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
      private final CategoryService categoryService;
    private final CategoryRepo categoryRepo;

    @PostMapping("/addCategory")
    public String addCategory(Model model,
                              @ModelAttribute("/newCategory") AddCategoryRequest addCategoryRequest
    ) throws IllegalAccessException {
        if (addCategoryRequest.getName().equals("")) {
            return "redirect:/admin/categories?message=categoryNull";
        }
        if (addCategoryRequest.getDescription().equals("")) {
            return "redirect:/admin/categories?message=categoryDescriptionNull";
        }
        CategoryDTO categoryDTO = categoryService.addCategory(addCategoryRequest);
        if (categoryDTO == null) {
            return "redirect:/admin/categories?message=categoryExist";
        } else {
            return "redirect:/admin/categories?message=addAccess";
        }

    }
    @PostMapping("/updateCategory")
    public String updateCategory(Model model,
                                 @ModelAttribute("/newCategory")AddCategoryRequest addCategoryRequest,
                                 @RequestParam("cateID") Integer cateID
    ) {
        if(addCategoryRequest.getName().equals("")) {
            return "redirect:/admin/categories?message=categoryNull";
        }if(addCategoryRequest.getDescription().equals("")) {
            return "redirect:/admin/categories?message=categoryDescriptionNull";
        }

        CategoryDTO categoryDTO = categoryService.updateCategory(addCategoryRequest, cateID);
        model.addAttribute("newCategory", new CategoryDTO());
        List<CategoryDTO> categoryList = categoryService.getAll();
        model.addAttribute("categories", categoryList);
        if (categoryDTO != null) {
            model.addAttribute("message", "Cập nhật thành công");
            return "admin/quanLyDanhMuc";
        } else {
            model.addAttribute("edit", true);
            model.addAttribute("message", "Cập nhật thất bại");
            return "admin/quanLyDanhMuc";
        }
    }
    @GetMapping("/searchForUpdateCate")
    public String searchForUpdate(@RequestParam("cateID") Integer cateID,
                                  org.springframework.ui.Model model) {
        Optional<CategoryEntity> catefind = categoryRepo.findById(cateID);
        List<CategoryDTO> categoryList = categoryService.getAll();
        model.addAttribute("categories", categoryList);
        if (catefind.isPresent()) {
            CategoryEntity cate = catefind.get();
            model.addAttribute("newCategory", CategoryDTO.builder()
                    .categoryId(cate.getCategoryId())
                    .name(cate.getName())
                    .description(cate.getDescription())
                    .build());
            model.addAttribute("edit", true);
            model.addAttribute("cateID", cateID);
            return "admin/quanLyDanhMuc";
        } else {
            model.addAttribute("newCategory", new CategoryDTO());
            model.addAttribute("message", "Không tìm thấy danh mục");
            return "admin/quanLyDanhMuc";
        }
    }

}
