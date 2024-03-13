package com.example.demo.domain.Post.dto;


import com.example.demo.domain.Post.dto.PostDTO;
import com.example.demo.domain.Post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDto(Post post);
}
