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
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asm.ecommerce.model.Category;
import com.asm.ecommerce.model.Product;
import com.asm.ecommerce.model.UserDtls;
import com.asm.ecommerce.service.CartService;
import com.asm.ecommerce.service.CategoryService;
import com.asm.ecommerce.service.ProductService;
import com.asm.ecommerce.service.UserService;
import com.asm.ecommerce.util.CommonUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class homeController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CartService cartService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

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
	public String index(Model m) {

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		List<Product> allActiveProducts = productService.getAllActiveProducts("").stream()
				.sorted((p1,p2)->Integer.compare(p2.getId(), p1.getId()))
				.limit(200).toList();
		m.addAttribute("category", allActiveCategory);
		m.addAttribute("products", allActiveProducts);
		return "index";
	}

	@GetMapping("home")
	public String home(){
		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	// @GetMapping("/products")
	// public String products(Model m, @RequestParam(value = "category", defaultValue = "") String category) {
	// 	List<Category> categories = categoryService.getAllActiveCategory();
	// 	List<Product> products = productService.getAllActiveProducts(category);
	// 	m.addAttribute("categories", categories);
	// 	m.addAttribute("products", products);
	// 	m.addAttribute("paramValue", category);
	// 	return "product";
	// }

	@GetMapping("/product/{id}")
	public String product(@PathVariable int id, Model m) {
		Product productById = productService.getProductById(id);
		m.addAttribute("product", productById);
		return "product-detail";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file,
			@RequestParam String email, @RequestParam String cpassword, @RequestParam String password,
			HttpSession session, HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		Boolean existsEmail = userService.existsEmail(user.getEmail());
		if (existsEmail) {
			session.setAttribute("errorMsg", "Email already exist");
		} else {
			if (!cpassword.equals(password)) {
				session.setAttribute("errorMsg", "Pass is not value!");
				return "redirect:/register";
			}
			String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
			user.setProfileImage(imageName);
			UserDtls saveUser = userService.saveUser(user);

			if (!ObjectUtils.isEmpty(saveUser)) {
				if (!file.isEmpty()) {
					String pathToSave = "src/main/resources/static/img/avata_img";
					File imgDir = new File(pathToSave);

					if (!imgDir.exists()) {
						imgDir.mkdirs();
					}

					Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
					// System.out.println("Saving image to: " + path.toString());

					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				}
				session.setAttribute("succMsg", "Register successfully");
			} else {
				session.setAttribute("errorMsg", "something wrong on server");
			}
		}
		return "redirect:/signin";
	}

	@GetMapping("/forgot-password")
	public String forgotPass() {
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String processForgotPassword(@RequestParam String email, HttpSession session, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {

		UserDtls userByEmail = userService.getUserByEmail(email);

		if (ObjectUtils.isEmpty(userByEmail)) {
			session.setAttribute("errorMsg", "Invalid email");
		} else {

			String resetToken = UUID.randomUUID().toString();
			userService.updateUserResetToken(email, resetToken);

			String url = CommonUtil.generateUrl(request) + "/reset-password?token=" + resetToken;

			Boolean sendMail = commonUtil.sendMail(url, email);

			if (sendMail) {
				session.setAttribute("succMsg", "Please check your email..Password Reset link sent");
			} else {
				session.setAttribute("errorMsg", "Somethong wrong on server ! Email not send");
			}
		}

		return "redirect:/forgot-password";
	}

	@GetMapping("/reset-password")
	public String showResetPassword(@RequestParam String token, HttpSession session, Model m) {

		UserDtls userByToken = userService.getUserByToken(token);

		if (userByToken == null) {
			m.addAttribute("msg", "Your link is invalid or expired !!");
			return "message";
		}
		m.addAttribute("token", token);
		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String passCF,
			HttpSession session,
			Model m) {

		UserDtls userByToken = userService.getUserByToken(token);
		if (userByToken == null) {
			m.addAttribute("errorMsg", "Your link is invalid or expired !!");
			return "message";
		} else {
			if (passCF.equals(password)) {
				userByToken.setPassword(passwordEncoder.encode(password));
				userByToken.setResetToken(null);
				userService.updateUser(userByToken);
				m.addAttribute("msg", "Password change successfully");
				return "message";
			}
			m.addAttribute("msg", "Confirm wrong password! Please try again.");
			return "message";
		}
	}

	@GetMapping("/search")
	public String searchProduct(
			@RequestParam String ch,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize,
			Model m) {

		Page<Product> searchProductsPage = productService.searchProductPagination(pageNo, pageSize, ch);

		m.addAttribute("products", searchProductsPage.getContent());
		m.addAttribute("productsSize", searchProductsPage.getContent().size());
		m.addAttribute("totalPages", searchProductsPage.getTotalPages());
		m.addAttribute("currentPage", pageNo);
		m.addAttribute("totalElements", searchProductsPage.getTotalElements());
		m.addAttribute("pageNo", searchProductsPage.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("ch", ch);
		m.addAttribute("isFirst", searchProductsPage.isFirst());
		m.addAttribute("isLast", searchProductsPage.isLast());

		List<Category> categories = categoryService.getAllActiveCategory();
		m.addAttribute("categories", categories);

		return "product";
	}

	@GetMapping("/products")
	public String products(Model m, @RequestParam(value = "category", defaultValue = "") String category,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize) {

		List<Category> categories = categoryService.getAllActiveCategory();
		m.addAttribute("paramValue", category);
		m.addAttribute("categories", categories);

		Page<Product> page = productService.getAllActiveProductPagination(pageNo, pageSize, category);
		List<Product> products = page.getContent();
		m.addAttribute("products", products);
		m.addAttribute("productsSize", products.size());

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "product";
	}

	@GetMapping("/terms-and-conditions")
	public String termsAndConditions() {
		return "terms-and-conditions";
	}

	@GetMapping("/privacy-policy")
	public String privacyPolicy() {
		return "privacy-policy";
	}

	@GetMapping("/copyright")
	public String copyright() {
		return "copyright";
	}

	@GetMapping("/hotro")
	public String hotro() {
		return "hotro";
	}
}
