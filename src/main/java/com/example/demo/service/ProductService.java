package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ProductsRepo;
import com.example.demo.request.AddProductRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepo productsRepo;
    private final CategoryRepo categoryRepo;

    public List<ProductDTO> getAll() {
        return productsRepo.findAll().stream()
                .filter(st ->
                        st.getStatus().equals("available")
                )
                .map(pr ->
                        ProductDTO.builder()
                                .productId(pr.getProductId())
                                .name(pr.getName())
                                .description(pr.getDescription())
                                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(pr.getPrice()))
                                .stockQuantity(pr.getStockQuantity())
                                .category(pr.getCategory())
                                .imageUrl(pr.getImageUrl())
                                .status(pr.getStatus())
                                .createdAt(pr.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .updatedAt(pr.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .build()
                ).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllForAdmin() {
        return productsRepo.findAll().stream()
                .map(pr ->
                        ProductDTO.builder()
                                .productId(pr.getProductId())
                                .name(pr.getName())
                                .description(pr.getDescription())
                                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(pr.getPrice()))
                                .stockQuantity(pr.getStockQuantity())
                                .category(pr.getCategory())
                                .imageUrl(pr.getImageUrl())
                                .status(pr.getStatus())
                                .createdAt(pr.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .updatedAt(pr.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .build()
                ).collect(Collectors.toList());
    }

    public List<ProductDTO> getAllByPagination(int page, int size) {
        Pageable pagination = PageRequest.of(page, size);

        return productsRepo.findAll(pagination).stream()
                .filter(st ->
                        st.getStatus().equals("available")
                )
                .map(pr ->
                        ProductDTO.builder()
                                .productId(pr.getProductId())
                                .name(pr.getName())
                                .description(pr.getDescription())
                                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(pr.getPrice()))
                                .stockQuantity(pr.getStockQuantity())
                                .category(pr.getCategory())
                                .imageUrl(pr.getImageUrl())
                                .status(pr.getStatus())
                                .createdAt(pr.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .updatedAt(pr.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .build()
                ).collect(Collectors.toList());
    }

    public ProductDTO getProductById(int id) {

        ProductsEntity productsEntity = productsRepo.findById(id).orElse(null);
        if (productsEntity == null) {
            return null;
        }
        return ProductDTO.builder()
                .productId(productsEntity.getProductId())
                .name(productsEntity.getName())
                .description(productsEntity.getDescription())
                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(productsEntity.getPrice()))
                .stockQuantity(productsEntity.getStockQuantity())
                .category(productsEntity.getCategory())
                .imageUrl(productsEntity.getImageUrl())
                .status(productsEntity.getStatus())
                .createdAt(productsEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .updatedAt(productsEntity.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
    }


    @Transactional
    public ProductDTO addProduct(AddProductRequest addProductRequest, String urlImg) {
        CategoryEntity categoryEntity = categoryRepo.findById(addProductRequest.getCategoryId()).orElse(null);
        if (categoryEntity == null) {
            return null;
        }

        ProductsEntity productSave = new ProductsEntity();
        productSave.setName(addProductRequest.getName());
        productSave.setDescription(addProductRequest.getDescription());
        productSave.setPrice(addProductRequest.getPrice());
        productSave.setStockQuantity(addProductRequest.getStockQuantity());
        productSave.setCategory(categoryEntity);
        productSave.setImageUrl(urlImg);
        productSave.setStatus("available");
        productSave.setCreatedAt(LocalDateTime.now());

        ProductsEntity savedProduct;
        try {
            savedProduct = productsRepo.save(productSave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ProductDTO.builder()
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(savedProduct.getPrice()))
                .stockQuantity(savedProduct.getStockQuantity())
                .category(savedProduct.getCategory())
                .imageUrl(savedProduct.getImageUrl())
                .status(savedProduct.getStatus())
                .build();
    }

    public ProductDTO deleteProduct(Integer prdID) {
        ProductsEntity productsEntity = productsRepo.findById(prdID).orElse(null);
        if (productsEntity == null) {
            return null;
        }
        productsEntity.setStatus("notAvailable");
        ProductsEntity savedProduct;
        try {
            savedProduct = productsRepo.save(productsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ProductDTO.builder()
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(savedProduct.getPrice()))
                .stockQuantity(savedProduct.getStockQuantity())
                .category(savedProduct.getCategory())
                .imageUrl(savedProduct.getImageUrl())
                .status(savedProduct.getStatus())
                .build();
    }

    public AddProductRequest findForUpdate(int id) {

        ProductsEntity productsEntity = productsRepo.findById(id).orElse(null);
        if (productsEntity == null) {
            return null;
        }
        return AddProductRequest.builder()
                .productId(productsEntity.getProductId())
                .name(productsEntity.getName())
                .description(productsEntity.getDescription())
                .price(productsEntity.getPrice())
                .stockQuantity(productsEntity.getStockQuantity())
                .categoryId(productsEntity.getCategory().getCategoryId())
                .build();
    }

    @Transactional
    public ProductDTO updateProduct(AddProductRequest addProductRequest, String urlImg) {
        CategoryEntity categoryEntity = categoryRepo.findById(addProductRequest.getCategoryId()).orElse(null);
        ProductsEntity productSave = productsRepo.findById(addProductRequest.getProductId()).orElse(null);
        if (categoryEntity == null || productSave == null) {
            return null;
        }
        ;
        productSave.setName(addProductRequest.getName());
        productSave.setDescription(addProductRequest.getDescription());
        productSave.setPrice(addProductRequest.getPrice());
        productSave.setStockQuantity(addProductRequest.getStockQuantity());
        productSave.setCategory(categoryEntity);
        productSave.setImageUrl(urlImg);
        productSave.setStatus("available");
        productSave.setUpdatedAt(LocalDateTime.now());

        ProductsEntity savedProduct;
        try {
            savedProduct = productsRepo.save(productSave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ProductDTO.builder()
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(NumberFormat.getInstance(new Locale("vi", "VN")).format(savedProduct.getPrice()))
                .stockQuantity(savedProduct.getStockQuantity())
                .category(savedProduct.getCategory())
                .imageUrl(savedProduct.getImageUrl())
                .status(savedProduct.getStatus())
                .build();
    }
}
