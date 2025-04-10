package com.example.demo.service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.request.AddCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;
    public List<CategoryDTO> getAll(){
        return categoryRepo.findAll().stream()
            .map(pr ->
                 CategoryDTO.builder()
                         .categoryId(pr.getCategoryId())
                         .name(pr.getName())
                         .description(pr.getDescription())
                         .build()
                    ).collect(Collectors.toList());
        }
//    public CategoryDTO getCategoryById(int id){
//        CategoryEntity categoryEntity = categoryRepo.findById(id).orElse(null);
//        return CategoryDTO.builder()
//                .categoryId(categoryEntity.getCategoryId())
//                .name(categoryEntity.getName())
//                .description(categoryEntity.getDescription())
//                .build();
//    }
    @Transactional
    public CategoryDTO addCategory(AddCategoryRequest addCategoryRequest){
        CategoryEntity categoryEntity = categoryRepo.findByName(addCategoryRequest.getName()).orElse(null);
        if(categoryEntity != null){
            return null;
        }
        CategoryEntity categorySave = new CategoryEntity();
        categorySave.setName(addCategoryRequest.getName());
        categorySave.setDescription(addCategoryRequest.getDescription());

        CategoryEntity savedCategory;
        try {
            savedCategory = categoryRepo.save(categorySave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return CategoryDTO.builder()
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .build();
    }
    public CategoryDTO updateCategory(AddCategoryRequest addCategoryRequest, Integer cateID){
        CategoryEntity categoryEntity = categoryRepo.findById(cateID).orElse(null);
        if(categoryEntity == null){
            return null;
        }
        categoryEntity.setName(addCategoryRequest.getName());
        categoryEntity.setDescription(addCategoryRequest.getDescription());
        CategoryEntity savedCategory;
        try {
            savedCategory = categoryRepo.save(categoryEntity);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return CategoryDTO.builder()
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .build();
    }
}
