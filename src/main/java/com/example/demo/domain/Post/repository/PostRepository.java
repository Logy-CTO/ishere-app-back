package com.example.demo.domain.Post.repository;

import com.example.demo.domain.Post.entity.Post;
import com.example.demo.domain.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PostRepository extends JpaRepository<Post, Integer> { //첫 번째는 엔티티 클래스 타입(Post), 두 번째는 해당 엔티티의 ID 필드 타입(Integer)
    long count();
    List<Post> findByPostIdIn(List<Integer> postIds);
    Page<Post> findBycategoryTypeOrderByIdDesc(String categoryType, Pageable pageable);

    Page<Post> findAllOrderByIdDesc(Pageable pageable);

    List<Post> findByUserName(String userName);

}
