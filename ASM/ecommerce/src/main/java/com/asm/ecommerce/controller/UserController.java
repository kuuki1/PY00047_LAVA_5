package com.asm.ecommerce.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.asm.ecommerce.model.Cart;
import com.asm.ecommerce.model.Category;
import com.asm.ecommerce.model.OrderRequest;
import com.asm.ecommerce.model.ProductOrder;
import com.asm.ecommerce.model.UserDtls;
import com.asm.ecommerce.service.CartService;
import com.asm.ecommerce.service.CategoryService;
import com.asm.ecommerce.service.OrderService;
import com.asm.ecommerce.service.UserService;
import com.asm.ecommerce.util.CommonUtil;
import com.asm.ecommerce.util.OrderStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

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

	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid,HttpSession session) {
		Cart saveCart = cartService.saveCart(pid, uid);
		
		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Product add to cart failed");
		}else {
			session.setAttribute("succMsg", "Product added to cart");
		}
		return "redirect:/product/" + pid;
	}

	@GetMapping("/addToCart/{id}")
	public String addCart(Principal p,Model m,@PathVariable int id, HttpSession session){
		if(p != null){
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			Cart saveCart = cartService.saveCart(id, userDtls.getId());
			if (ObjectUtils.isEmpty(saveCart)) {
				session.setAttribute("errorMsg", "Product add to cart failed");
			}else {
				session.setAttribute("succMsg", "Product added to cart");
			}
		}
		return"redirect:/user/cart";
	}

	@GetMapping("/cart")
	public String loadCartPage(Principal p, Model m) {

		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());

		if (carts != null && !carts.isEmpty()) {
			m.addAttribute("carts", carts);
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		} else {
			m.addAttribute("carts", new ArrayList<>());
			m.addAttribute("totalOrderPrice", 0.0);
		}

		return "/user/cart";
	}


	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		cartService.updateQuantity(sy, cid);
		return "redirect:/user/cart";
	}

	@GetMapping("/orders")
	public String orderPage(Principal p, Model m) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("orderPrice", orderPrice);
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/order";
	}

	@PostMapping("/save-order")
	public String saveOrder(@ModelAttribute OrderRequest request, @RequestParam String pincode, Principal p, HttpSession session) throws Exception {
		UserDtls user = getLoggedInUserDetails(p);
		if(!user.getPincode().equals(pincode)){
			session.setAttribute("errorMsg", "Wrong pin code!");
			return"redirect:/user/orders";
		}else {
			request.setFirstName(" ");
			request.setAddress(user.getAddress());
			request.setLastName(user.getName());
			request.setCity(user.getCity());
			request.setEmail(user.getEmail());
			request.setMobileNo(user.getMobileNumber());
			orderService.saveOrder(user.getId(), request);
			cartService.deleteAllCartByUser(user.getId());
		}
		
		return "redirect:/user/success";
	}

	@GetMapping("/success")
	public String loadSuccess() {
		return "/user/success";
	}

	@GetMapping("/user-orders")
	public String myOrder(Model m, Principal p) {
		UserDtls loginUser = getLoggedInUserDetails(p);
		List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
		orders.sort(Comparator.comparing(ProductOrder::getOrderDate).reversed());
		m.addAttribute("orders", orders);
		return "/user/my_orders";
	}

	@GetMapping("/update-status")
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
		orderService.deleteOrderByOrderId(updateOrder.getOrderId());
		return "redirect:/user/user-orders";
	}

	@GetMapping("/profile")
	public String profile() {
		return "/user/profile";
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
		return "redirect:/user/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);

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

		return "redirect:/user/profile";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

    @GetMapping("/")
	public String home() {
		return "user/index";
	}
}
