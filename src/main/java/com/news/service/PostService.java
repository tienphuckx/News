package com.news.service;

import java.util.List;

import com.news.entity.Post;

public interface PostService {
	List<Post> list();

	Post create(Post category);

	Post update(int id, Post category);

	Post delete(int id);

	Post findById(int id);

	List<Post> findNewest();

	List<Post> findByPin(boolean pin);
	List<Post> findByCategoryId(int categoryId);
}
