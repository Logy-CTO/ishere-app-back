package com.example.demo.domain.File;

import com.example.demo.domain.Post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime created_at;

    @Column(name = "image_name")
    private String image_name;

    @Column(name = "img_url")
    private String img_url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Images(String image_name,
                  String img_url,
                  Post post)
    {
        this.image_name = image_name;
        this.img_url = img_url;
        this.post = post;
    }


}
