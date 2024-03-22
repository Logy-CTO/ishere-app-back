package com.example.demo.domain.Post.repository;

import com.example.demo.domain.Post.entity.InterestPost;
import com.example.demo.domain.Post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestPostRepository extends JpaRepository<InterestPost, Long> {
    List<InterestPost> findByUserName(String userName);
}