package com.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findTop6ByOrderByIdDesc();
	List<Post> findByPin(boolean pin);
	List<Post> findTop6ByPinOrderByIdDesc(boolean pin);
	List<Post> findByCategoryIdAndPin(int categoryId, boolean pin);
}
