package com.news.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.news.entity.Post;
import com.news.repository.PostRepository;
import com.news.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Post> list() {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}

	@Override
	public Post create(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post update(int id, Post post) {
		// TODO Auto-generated method stub
		post.setId(id);
		return postRepository.save(post);
	}

	@Override
	public Post delete(int id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Categry Id:" + id));
		postRepository.delete(post);
		return post;
	}

	@Override
	public Post findById(int id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
		return post;
	}

	@Override
	public List<Post> findNewest() {
		return postRepository.findTop6ByPinOrderByIdDesc(false);
	}

	@Override
	public List<Post> findByPin(boolean pin) {
		return postRepository.findByPin(pin);
	}

	@Override
	public List<Post> findByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return postRepository.findByCategoryIdAndPin(categoryId, false);
	}
}
