package com.example.demo.domain.Post.LocationFind;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;

//한눈에 보기 기능을 위한 postId, postTitle, 위도(xLoc), 경도(yLoc)값 저장 엔티티
@Entity
@Table(name = "location_find")
@Getter
@Setter
public class LocationFind {
    @Id
    @Column(name = "POST_ID")
    private int postId;
    @Column(name = "POST_TITLE")
    private String postTitle;
    @Column(name = "X_LOC")
    private double xLoc;
    @Column(name = "Y_LOC")
    private double yLoc;
    @Column(name = "IMMEDIATE_CASE")
    private byte immediateCase;

}