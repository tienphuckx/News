package com.news.admin.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.entity.Category;
import com.news.entity.Post;
import com.news.request.PostRequest;
import com.news.service.CategoryService;
import com.news.service.FilesStorageService;
import com.news.service.PostService;
import com.news.util.NewsUtil;

@Controller
@RequestMapping("/post")
public class PostController {

	private static final String POST_URL = "admin/post";
	private final PostService postService;
	private final CategoryService categoryService;
	private final FilesStorageService storageService;

	@Autowired
	public PostController(PostService postService, CategoryService categoryService,
			FilesStorageService storageService) {
		this.postService = postService;
		this.categoryService = categoryService;
		this.storageService = storageService;
	}

	// display list of employees
	@GetMapping
	public String viewList(Model model, HttpSession session) {
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		model.addAttribute("posts", postService.list());
		return POST_URL + "/index";
	}

	@GetMapping("/add")
	public String viewAdd(Model model, HttpSession session) {
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		model.addAttribute("categories", categoryService.list());
		model.addAttribute("post", new PostRequest());
		return POST_URL + "/add";
	}

	@GetMapping("/edit/{id}")
	public String findById(@PathVariable("id") int id, Model model, HttpSession session) {
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		Post post = postService.findById(id);
		PostRequest request = new PostRequest();
		request.setCategoryId(post.getCategory().getId());
		request.setId(id);
		request.setTitle(post.getTitle());
		request.setDescription(post.getDescription());
		request.setPin(post.isPin());
		request.setImageName(post.getImage());
		model.addAttribute("post", request);
		model.addAttribute("categories", categoryService.list());
		return POST_URL + "/update";
	}

	@PostMapping
	public String add(@Valid PostRequest postRequest, BindingResult result, Model model, HttpSession session) {
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		if (result.hasErrors()) {
			return POST_URL + "add";
		}
		
		
		Post post = new Post();
		post.setPin(postRequest.isPin());
		
		post.setTitle(postRequest.getTitle());
		post.setDescription(postRequest.getDescription());
		Category category = categoryService.findById(postRequest.getCategoryId());
		post.setCategory(category);
		post.setCreatedDate(new Date());
		if(postRequest.getImage() != null) {
			String fileName = postRequest.getImage().getOriginalFilename() + "_" + Calendar.getInstance().getTimeInMillis();
			storageService.save(postRequest.getImage(), fileName);
			post.setImage(fileName);
		}
		postService.create(post);
		return "redirect:/post";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable("id") int id, @Valid PostRequest postRequest, BindingResult result, Model model,
			HttpSession session) {
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		if (result.hasErrors()) {
			postRequest.setId(id);
			model.addAttribute("post", postRequest);
			return POST_URL + "/update";
		}
		Post post = new Post();
		post.setPin(postRequest.isPin());
		post.setTitle(postRequest.getTitle());
		post.setDescription(postRequest.getDescription());
		Category category = categoryService.findById(postRequest.getCategoryId());
		post.setCategory(category);
		post.setCreatedDate(new Date());
		if (postRequest.getImage() != null) {
			String fileName = postRequest.getImage().getOriginalFilename() + "_" + Calendar.getInstance().getTimeInMillis();
			storageService.save(postRequest.getImage(), fileName);
			post.setImage(fileName);
		} else {
			post.setImage(postService.findById(id).getImage());
		}
		postService.update(id, post);
		model.addAttribute("posts", postService.list());
		return POST_URL + "/index";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model, HttpSession session) {
		if (!NewsUtil.isLogin(session)) {
			return "redirect:/login";
		}
		postService.delete(id);
		return "redirect:/post";
	}
}
