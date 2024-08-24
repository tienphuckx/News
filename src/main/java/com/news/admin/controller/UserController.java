package com.news.admin.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.constant.ROLE;
import com.news.entity.User;
import com.news.service.UserService;
import com.news.util.NewsUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final String USER_URL = "admin/user";
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// display list of employees
	@GetMapping()
	public String viewList(Model model, HttpSession session) {
		if(!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		model.addAttribute("users", userService.list());
		return USER_URL + "/index";
	}

	@GetMapping("/add")
	public String viewAdd(Model model, HttpSession session) {
		if(!NewsUtil.isLogin(session)) {
			return "/login";
		}
		model.addAttribute("user", new User());
		
		return USER_URL + "/add";
	}

	@GetMapping("/edit/{id}")
	public String findById(@PathVariable("id") int id, Model model, HttpSession session) {
		if(!NewsUtil.isLogin(session)) {
			return "/login";
		}
		model.addAttribute("user", userService.findById(id));
		return USER_URL + "/update";
	}

	@PostMapping()
	public String add(@Valid User user, BindingResult result, Model model, HttpSession session) {
		if(!NewsUtil.isLogin(session)) {
			return "/login";
		}
		if (result.hasErrors()) {
			return USER_URL + "/add";
		}
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
		user.setRole(ROLE.ADMIN);
		userService.create(user);
		return "redirect:/user";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model, HttpSession session) {
		if(!NewsUtil.isLogin(session)) {
			return "/login";
		}
		userService.delete(id);
		return "redirect:/user";
	}
}
