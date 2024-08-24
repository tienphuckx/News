package com.news.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.news.entity.Category;
import com.news.repository.CategoryRepository;
import com.news.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category create(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category update(int id, Category category) {
		// TODO Auto-generated method stub
		category.setId(id);
		return categoryRepository.save(category);
	}

	@Override
	public Category delete(int id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Categry Id:" + id));
		categoryRepository.delete(category);
		return category;
	}

	@Override
	public Category findById(int id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
		return category;
	}

	@Override
	public List<Category> findTop5ByOrderByIdDesc() {
		// TODO Auto-generated method stub
		return categoryRepository.findTop5ByOrderByIdDesc();
	}
}
