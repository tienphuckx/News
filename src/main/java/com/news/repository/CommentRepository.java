package com.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findTopByPostIdOrderByIdDesc(int postId);
	List<Comment> findByPostIdOrderByIdDesc(int postId);
}
