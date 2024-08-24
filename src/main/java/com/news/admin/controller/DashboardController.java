package com.news.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.service.CategoryService;
import com.news.service.PostService;
import com.news.util.NewsUtil;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	private final PostService postService;
	private final CategoryService categoryService;

	@Autowired
	public DashboardController(PostService postService, CategoryService categoryService) {
		this.postService = postService;
		this.categoryService = categoryService;

	}

	// display list of employees
	@GetMapping
	public String viewHomePage(Model model, HttpSession session) {
//        model.addAttribute("listEmployees", employeeService.getAllEmployees());
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		model.addAttribute("totalCategory", categoryService.list().size());
		model.addAttribute("totalPost", postService.list().size());
		return "admin/index";
	}
}
