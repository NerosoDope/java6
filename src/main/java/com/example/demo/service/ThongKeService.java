package com.example.demo.service;

import com.example.demo.dto.DoanhThuDTO;
import com.example.demo.repo.ProductsRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThongKeService {
    ProductsRepo productsRepo;

    public List<DoanhThuDTO> getDoanhThu(){

        return productsRepo.getRevenue().stream()
                .map( re ->
                        DoanhThuDTO.builder()
                                .productId((Integer) re[0])
                                .productName((String) re[1])
                                .productPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(((BigDecimal) re[2]).longValue()))
                                .totalQuantity(((Integer) re[3]).intValue())
                                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(((BigDecimal) re[4]).longValue()))
                                .build()
                        ).collect(Collectors.toList());
    }

    public List<DoanhThuDTO> getThongKeSanPham(){
        return productsRepo.getThongKeSanPham().stream()
                .map( re ->
                        DoanhThuDTO.builder()
                                .productId((Integer) re[0])
                                .productName((String) re[1])
                                .productPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(((BigDecimal) re[2]).longValue()))
                                .totalQuantity((re[3])!= null?((Integer) re[3]).intValue():0)
                                .totalCancel((re[4])!= null?((Integer) re[4]).intValue():0)
                                .createDate(((Timestamp) re[5]).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                .build()
                ).collect(Collectors.toList());
    }

}
