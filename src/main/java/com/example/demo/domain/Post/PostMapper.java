package com.example.demo.domain.Post;


import com.example.demo.domain.Post.DTO.PostDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDto(Post post);
}
