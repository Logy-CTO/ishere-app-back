package com.example.demo.Area;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByAreaNameStartingWith(String areaName);
    //AreaName 필드 값이 특정 문자열로 시작하는 모든 엔티티를 찾는데 사용(KHW)
}