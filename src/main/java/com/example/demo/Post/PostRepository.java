package com.example.demo.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> { //첫 번째는 엔티티 클래스 타입(Post), 두 번째는 해당 엔티티의 ID 필드 타입(Integer)
    long count();
    List<Post> findByUserId(long userId);
}
