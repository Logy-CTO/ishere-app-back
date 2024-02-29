package com.example.demo.domain.Post.LocationFind;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationFindRepository extends JpaRepository<LocationFind, Integer> {

}