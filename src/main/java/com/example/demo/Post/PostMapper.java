package com.example.demo.Post;

import com.example.demo.User.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    public Post postDtoToPost(PostDTO PostDTO);
}
