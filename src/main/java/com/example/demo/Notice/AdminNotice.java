package com.example.demo.Notice;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "NOTICE")
public class AdminNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;


    private String title;

    private String description;

    private String img_url;

    private String created_at;
}
