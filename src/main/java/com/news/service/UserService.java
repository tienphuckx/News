package com.news.service;

import java.util.List;

import com.news.entity.User;

public interface UserService {
	List<User> list();
	User create(User category);
	User update(int id, User category);
	User delete(int id);
	User findById(int id);
	User findByUsername(String username);
}
