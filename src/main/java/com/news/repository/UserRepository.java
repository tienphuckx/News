package com.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);
}
