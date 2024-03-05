package com.example.demo.domain.Post.repository;

import com.example.demo.domain.Post.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
