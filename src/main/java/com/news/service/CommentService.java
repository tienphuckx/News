package com.news.service;

import java.util.List;

import com.news.entity.Comment;

public interface CommentService {
	List<Comment> list();
	Comment create(Comment category);
	Comment update(int id, Comment category);
	Comment delete(int id);
	Comment findById(int id);
	List<Comment> findTopComment(int postId);
}
