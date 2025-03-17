package com.asm.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asm.ecommerce.model.Category;
import com.asm.ecommerce.model.Product;
import com.asm.ecommerce.model.ProductOrder;
import com.asm.ecommerce.model.UserDtls;
import com.asm.ecommerce.service.CartService;
import com.asm.ecommerce.service.CategoryService;
import com.asm.ecommerce.service.OrderService;
import com.asm.ecommerce.service.ProductService;
import com.asm.ecommerce.service.ReportService;
import com.asm.ecommerce.service.UserService;
import com.asm.ecommerce.util.CommonUtil;
import com.asm.ecommerce.util.OrderStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class adminController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@GetMapping("/")
	public String index(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);

			if (userDtls != null && userDtls.getRole().equals("ROLE_ADMIN")) {
				return "admin/index";
			}
		}
		return "redirect:/home";
	}

	@GetMapping("/loadAddProduct")
	public String loadAddProduct(Model m) {
		List<Category> categories = categoryService.getAllCategory();
		m.addAttribute("categories", categories);
		return "admin/add_product";
	}

	@GetMapping("/category")
	public String category(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize) {
		Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
		List<Category> categorys = page.getContent();
		m.addAttribute("categorys", categorys);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/category";
	}

	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category,@RequestParam("file") MultipartFile file, HttpSession session) throws IOException{
		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		category.setImageName(imageName);

		Boolean existCategory = categoryService.existCategory(category.getName());

		if (existCategory) {
			session.setAttribute("errorMsg", "Category Name already exists");
		} else {
			Category saveCategory = categoryService.saveCategory(category);
			if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {
				String pathToSave = "src/main/resources/static/img/category_img";
				File imgDir = new File(pathToSave);

				if (!imgDir.exists()) {
					imgDir.mkdirs();
				}

				Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				System.out.println("Saving image to: " + path.toString());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				session.setAttribute("succMsg", "Saved successfully");
			}
		}

		return"redirect:/admin/category";
	}

	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Category category = categoryService.getCategoryById(id);
		List<Product> list = productService.getAllActiveProducts(category.getName());

		if(list.size() > 0) {
			session.setAttribute("errorMsg", "You should delete products related to this category first!");
		} else {
			Boolean deleteCategory = categoryService.deleteCategory(id);
			if (deleteCategory) {
				session.setAttribute("succMsg", "category delete success");
			} else {
				session.setAttribute("errorMsg", "something wrong on server");
			}
		}
		return "redirect:/admin/category";
	}

	@GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/edit_category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {

			if (!file.isEmpty()) {
				String pathToSave = "src/main/resources/static/img/category_img";
				File imgDir = new File(pathToSave);

				if (!imgDir.exists()) {
					imgDir.mkdirs();
				}

				Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				System.out.println("Saving image to: " + path.toString());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			session.setAttribute("succMsg", "Category update success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session, HttpServletRequest req, HttpServletResponse resp, @RequestParam Double price) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
		boolean a = true;
		product.setImage(imageName);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());
		product.setIsActive(a);

		Product saveProduct = productService.saveProduct(product);

		// if(price<0){
		// 	session.setAttribute("errorMsg", "something wrong on server");
		// }

		if (!ObjectUtils.isEmpty(saveProduct)) {

			String pathToSave = "src/main/resources/static/img/product_img";
			File imgDir = new File(pathToSave);

			if (!imgDir.exists()) {
				imgDir.mkdirs();
			}

			Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + image.getOriginalFilename());
			System.out.println("Saving image to: " + path.toString());

			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			session.setAttribute("succMsg", "Product Saved Success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadAddProduct";
	}

	@GetMapping("/products")
	public String loadViewProduct(Model m, @RequestParam(defaultValue = "") String ch,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize) {

		Page<Product> page = null;
		if (ch != null && ch.length() > 0) {
			page = productService.searchProductPagination(pageNo, pageSize, ch);
		} else {
			page = productService.getAllProductsPagination(pageNo, pageSize);
		}
		m.addAttribute("products", page.getContent());
		m.addAttribute("productsSize", page.getContent().size());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("currentPage", pageNo);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("ch", ch);
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		return "admin/products";
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpSession session) {
		Boolean deleteProduct = productService.deleteProduct(id);
		if (deleteProduct) {
			session.setAttribute("succMsg", "Product delete success");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/admin/products";
	}

	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model m) {

		m.addAttribute("product", productService.getProductById(id));
		//m.addAttribute("category", productService.getProductById(id).getCategory());
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/edit_product";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
		HttpSession session, Model m, HttpServletRequest req, HttpServletResponse resp) throws IOException {

			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			if (product.getDiscount() < 0 || product.getDiscount() > 100) {
				session.setAttribute("errorMsg", "invalid Discount");
			} else {
				Product updateProduct = productService.updateProduct(product, image);
				if (!ObjectUtils.isEmpty(updateProduct)) {
					session.setAttribute("succMsg", "Product update success");
				} else {
					session.setAttribute("errorMsg", "Something wrong on server");
				}
			}
			return "redirect:/admin/editProduct/" + product.getId();
			
	}

	@GetMapping("/users")
	public String getAllUsers(Model m) {
		List<UserDtls> users = userService.getUsers("ROLE_USER");
		m.addAttribute("users", users);
		return "/admin/users";
	}

	@GetMapping("/updateSts")
	public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Integer id, HttpSession session) {
		Boolean f = userService.updateAccountStatus(id, status);
		if (f) {
			session.setAttribute("succMsg", "Account Status Updated");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/admin/users";
	}

	@GetMapping("/orders")
	public String getAllOrders(Model m,@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize) {
		
		Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
		m.addAttribute("orders", page.getContent());
		m.addAttribute("srch", false);
	
		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());
		
		return "/admin/orders";
	}
	
	@PostMapping("/update-order-status")
	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
		
		try {
			commonUtil.sendMailForProductOrder(updateOrder, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!ObjectUtils.isEmpty(updateOrder)) {
			session.setAttribute("succMsg", "Status Updated");
		} else {
			session.setAttribute("errorMsg", "status not updated");
		}
		return "redirect:/admin/orders";
	}

	@GetMapping("/search-order")
	public String searchProduct(@RequestParam String orderId, Model m, HttpSession session,@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize) {

		if (orderId != null && orderId.length() > 0) {

			ProductOrder order = orderService.getOrdersByOrderId(orderId.trim());

			if (ObjectUtils.isEmpty(order)) {
				session.setAttribute("errorMsg", "Incorrect orderId");
				m.addAttribute("orderDtls", null);
			} else {
				m.addAttribute("orderDtls", order);
			}

			m.addAttribute("srch", true);
		} else {
			Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
			m.addAttribute("orders", page);
			m.addAttribute("srch", false);
			
			m.addAttribute("pageNo", page.getNumber());
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("totalElements", page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			m.addAttribute("isFirst", page.isFirst());
			m.addAttribute("isLast", page.isLast());
			
		}
		return "/admin/orders";
	}

	@GetMapping("/add-admin")
	public String loadAdminAdd() {
		return "/admin/add_admin";
	}

	@PostMapping("/save-admin")
	public String saveAdmin(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session, HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		user.setProfileImage(imageName);
		UserDtls saveUser = userService.saveAdmin(user);

		if (!ObjectUtils.isEmpty(saveUser)) {
			if (!file.isEmpty()) {
				String pathToSave = "src/main/resources/static/img/avata_img";
				File imgDir = new File(pathToSave);

				if (!imgDir.exists()) {
					imgDir.mkdirs();
				}

				Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}
			session.setAttribute("succMsg", "Register successfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/add-admin";
	}

	@GetMapping("/profile")
	public String profile() {
		return "/admin/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@RequestParam String name, @RequestParam String mobileNumber, @RequestParam String address, @RequestParam String city, @RequestParam String pincode,@RequestParam MultipartFile img, HttpSession session, Principal p, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);
		loggedInUserDetails.setName(name);
		loggedInUserDetails.setMobileNumber(mobileNumber);
		loggedInUserDetails.setAddress(address);
		loggedInUserDetails.setCity(city);
		loggedInUserDetails.setPincode(pincode);
		UserDtls updateUserProfile = userService.updateUserProfile(loggedInUserDetails, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Profile not updated");
		} else {
			session.setAttribute("succMsg", "Profile Updated");
		}
		return "redirect:/admin/profile";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = commonUtil.getLoggedInUserDetails(p);

		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());

		if (matches) {
			String encodePassword = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encodePassword);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if (ObjectUtils.isEmpty(updateUser)) {
				session.setAttribute("errorMsg", "Password not updated !! Error in server");
			} else {
				session.setAttribute("succMsg", "Password Updated sucessfully");
			}
		} else {
			session.setAttribute("errorMsg", "Current Password incorrect");
		}

		return "redirect:/admin/profile";
	}

	@GetMapping("/statistical-report")
    public String getStatisticalReport(Model model, @RequestParam(required = false) String reportType) {
		if (reportType == null) {
			reportType = "default";
		}
        switch (reportType) {
            case "total-products-sold-by-day":
                model.addAttribute("reportType", "Total Products Sold By Day");
                model.addAttribute("reportData", reportService.getTotalProductsSoldByDay());
                break;
            case "total-income-by-day":
                model.addAttribute("reportType", "Total Income By Day");
                model.addAttribute("reportData", reportService.getTotalIncomeByDay());
                break;
            case "total-products-sold-by-category":
                model.addAttribute("reportType", "Total Products Sold By Category");
                model.addAttribute("reportData", reportService.getTotalProductsSoldByCategory());
                break;
            case "total-products-sold-by-month":
                model.addAttribute("reportType", "Total Products Sold By Month");
                model.addAttribute("reportData", reportService.getTotalProductsSoldByMonth());
                break;
            case "total-income-by-month":
                model.addAttribute("reportType", "Total Income By Month");
                model.addAttribute("reportData", reportService.getTotalIncomeByMonth());
                break;
            case "total-products-sold-by-year":
                model.addAttribute("reportType", "Total Products Sold By Year");
                model.addAttribute("reportData", reportService.getTotalProductsSoldByYear());
                break;
            case "total-income-by-year":
                model.addAttribute("reportType", "Total Income By Year");
                model.addAttribute("reportData", reportService.getTotalIncomeByYear());
                break;
            default:
                model.addAttribute("reportType", "Please select a valid report type.");
                break;
        }
        return "admin/statistical-report";
    }
}
