package com.example.demo.domain.Post.repository;

import com.example.demo.domain.Post.entity.InterestPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestPostRepository extends JpaRepository<InterestPost, Long> {
    // 추가적인 메서드가 필요한 경우 여기에 선언할 수 있습니다.
}