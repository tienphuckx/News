package com.news.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.entity.Comment;
import com.news.service.CategoryService;
import com.news.service.CommentService;
import com.news.service.PostService;

@Controller
@RequestMapping("/")
public class HomeController {
	private final PostService postService;
	private final CategoryService categoryService;
	private final CommentService commentService;

	@Autowired
	public HomeController(PostService postService, CategoryService categoryService, CommentService commentService) {
		this.postService = postService;
		this.categoryService = categoryService;
		this.commentService = commentService;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("postPin", postService.findByPin(true));
		model.addAttribute("postNewest", postService.findNewest());
		model.addAttribute("categories", categoryService.findTop5ByOrderByIdDesc());
		model.addAttribute("categoriesMenu", categoryService.list());
		return "user/index";
	}

	@GetMapping("/blogs/{categoryId}")
	public String blogs(@PathVariable("categoryId") int categoryId, Model model) {
		model.addAttribute("posts", postService.findByCategoryId(categoryId));
		model.addAttribute("category", categoryService.findById(categoryId));
		model.addAttribute("categoriesMenu", categoryService.list());
		return "user/blogs";
	}

	@GetMapping("/blogs/detail/{id}")
	public String detail(@PathVariable("id") int id, Model model) {
		model.addAttribute("post", postService.findById(id));
//		model.addAttribute("topComment", commentService.findTopComment(id).size() > 0 ? commentService.findTopComment(id) : new Comment());
		model.addAttribute("comment", new Comment());
		model.addAttribute("categoriesMenu", categoryService.list());
		return "user/blog";
	}
}
