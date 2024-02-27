package com.example.demo.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PostRepository extends JpaRepository<Post, Integer> { //첫 번째는 엔티티 클래스 타입(Post), 두 번째는 해당 엔티티의 ID 필드 타입(Integer)
    long count();
    List<Post> findByUserName(String userName);

    List<Post> findByPostIdIn(List<Integer> postIds);

}
