package com.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findTop5ByOrderByIdDesc();
}
