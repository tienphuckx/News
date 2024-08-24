package com.news.user.controller;

import java.util.Calendar;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.constant.SESSION_ATTR;
import com.news.entity.Comment;
import com.news.entity.User;
import com.news.request.UserRequest;
import com.news.service.CommentService;
import com.news.service.PostService;
import com.news.service.UserService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	private final CommentService commentService;
	private final UserService userService;
	private final PostService postService;

	@Autowired
	public CommentController(CommentService commentService, UserService userService, PostService postService) {
		this.commentService = commentService;
		this.userService = userService;
		this.postService = postService;
	}

	@PostMapping
	public String add(@Valid Comment comment, Model model, int postId, HttpSession session) {
		UserRequest userSession = (UserRequest) session.getAttribute(SESSION_ATTR.USER_SESSION);
		if (userSession == null) {
			return "redirect:/signin";
		}
		User user = userService.findByUsername(userSession.getUsername());
		comment.setUser(user);
		comment.setPost(postService.findById(postId));
		comment.setCreatedAt(Calendar.getInstance().getTime());
		commentService.create(comment);
		return "redirect:/blogs/detail/" + postId;
	}
}
