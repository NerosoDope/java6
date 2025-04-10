package com.example.demo.controller.admin;

import com.example.demo.dto.*;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.OrderRepo;
import com.example.demo.repo.ProductsRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.request.AddCategoryRequest;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.AddStaffRequest;
import com.example.demo.service.*;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDisplayController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService customerService;
    private final UserService userService;
    private final CategoryRepo categoryRepo;
    private final EmployeeService employeeService;
    private final OrderService orderService;
    private final ThongKeService thongKeService;
    private final OrderRepo orderRepo;
    private final ProductsRepo productsRepo;
    private final UserRepo userRepo;


    @GetMapping("/")
    public String index(Model model) {
        List<OrderDTO> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);
        model.addAttribute("donHang", orderRepo.findAll().size());
        model.addAttribute("hoanTra", productsRepo.getSoDonBiHuy());
        model.addAttribute("soKhachHang", userRepo.findAll().size());
        return "admin/index";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam("message") Optional<String> message) {
        List<ProductDTO> productList = productService.getAllForAdmin();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("newProduct", new AddProductRequest());
        if (message.isPresent()) {
            if (message.get().equals("productExist")) {
                model.addAttribute("message", "Sản phẩm đã tồn tại");
            } else if (message.get().equals("addAccess")) {
                model.addAttribute("message", "Sản phẩm đã được thêm thành công");
            } else if (message.get().equals("emptyName")) {
                model.addAttribute("message", "Tên sản phẩm không được để trống");
            } else if (message.get().equals("PriceNull")) {
                model.addAttribute("message", "Giá tiền không được nhỏ hơn 0");
            } else if (message.get().equals("stockQuantityNull")) {
                model.addAttribute("message", "Số lượng sản phẩm phải lớn hơn 0");
            } else if (message.get().equals("dropAccess")) {
                model.addAttribute("message", "Xóa thành công, chuyển trạng thái thành ẩn sản phẩm");
            } else if (message.get().equals("dropFailed")) {
                model.addAttribute("message", "Xóa không thành công");
            } else if (message.get().equals("UpdateFailed")) {
                model.addAttribute("message", "Vui lòng nhập đủ thông tin");
            } else if (message.get().equals("UpdateAccess")) {
                model.addAttribute("message", "Cập nhật thành công");
            }
        }
        return "admin/quanLySanPham";
    }

    @GetMapping("/customers")
    public String customers(Model model) {
        List<UserDTO> customerList = customerService.getAllCustomers();
        model.addAttribute("customers", customerList);
        return "admin/quanLyKhachHang";
    }

    @GetMapping("/employees")
    public String employees(Model model, @RequestParam("message") Optional<String> message) {
        List<UserDTO> employeeList = employeeService.getAllEmployees();
        model.addAttribute("employees", employeeList);
        model.addAttribute("newStaff", new AddStaffRequest());
        if (message.isPresent()) {
            if (message.get().equals("categoryExist")) {
                model.addAttribute("message", "Tài khoản nhân viên đã tồn tại");
            } else if (message.get().equals("addAccess")) {
                model.addAttribute("message", "Tài khoản nhân viên đã được thêm thành công");
            } else if (message.get().equals("emptyFullName")) {
                model.addAttribute("message", "Họ và tên không được để trống");
            } else if (message.get().equals("emptyUserName")) {
                model.addAttribute("message", "Tên người dùng không được để trống ");
            } else if (message.get().equals("emptyPassword")) {
                model.addAttribute("message", "Mật khẩu không được để trống");
            } else if (message.get().equals("emptyEmail")) {
                model.addAttribute("message", "Email không được để trống");
            } else if (message.get().equals("emptyPhone")) {
                model.addAttribute("message", "Số điện thoại không được để trống");
            } else if (message.get().equals("failPhone")) {
                model.addAttribute("message", "Số điện thoại không đúng định dạng");
            } else if (message.get().equals("deleteFailed")) {
                model.addAttribute("message", "Xóa không thành công");
            } else if (message.get().equals("deleteAccess")) {
                model.addAttribute("message", "Xóa thành công");
            }
        }
        return "admin/quanLyNhanVien";
    }

    @GetMapping("/categories")
    public String categories(Model model, @RequestParam("message") Optional<String> message) {
        List<CategoryDTO> categoryList = categoryService.getAll();
        model.addAttribute("categories", categoryList);
        model.addAttribute("newCategory", new AddCategoryRequest());
        if (message.isPresent()) {
            if (message.get().equals("categoryExist")) {
                model.addAttribute("message", "Danh mục đã tồn tại");
            } else if (message.get().equals("addAccess")) {
                model.addAttribute("message", "Danh mục đã được thêm thành công");

            } else if (message.get().equals("categoryNull")) {
                model.addAttribute("message", "Tên nhà cung cấp không được để trống");
            } else if (message.get().equals("categoryDescriptionNull")) {
                model.addAttribute("message", "Mô tả không được để trống");
            }
        }
        return "admin/quanLyDanhMuc";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        List<OrderDTO> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);
        return "admin/quanLyDonHang";
    }
    @GetMapping("/payment")
    public String payment(Model model) {
        List<OrderDTO> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);
        return "admin/quanLyThanhToan";
    }

    @GetMapping("/doanhThu")
    public String doanhthu(Model model) {
        List<DoanhThuDTO> doanhThuList = thongKeService.getDoanhThu();
        model.addAttribute("doanhThuList", doanhThuList);
        model.addAttribute("spBanDuoc", productsRepo.getSanPhamBanDuoc());
        model.addAttribute("soKhachHang", orderRepo.getSoKH());
        model.addAttribute("tongDoanhThu", NumberFormat.getInstance(new Locale("vi", "VN")).format(orderRepo.getTongDoanhThu()));
        model.addAttribute("soDonHang", orderRepo.getSoDonHang());
        return "admin/thongKeDoanhThu";
    }

    @GetMapping("/spDaBan")
    public String daban(Model model) {
        model.addAttribute("listSpDaban", thongKeService.getThongKeSanPham());
        model.addAttribute("spBanDuoc", productsRepo.getSanPhamBanDuoc());
        model.addAttribute("soHoanTra", productsRepo.getSoDonBiHuy());
        model.addAttribute("maxLuotBan", ((String) productsRepo.getMaxLuotBan()));
        model.addAttribute("soDonHang", orderRepo.getSoDonHang());
        return "admin/sanPhamDaBan";
    }
}
