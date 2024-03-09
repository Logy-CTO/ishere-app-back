package com.example.demo.domain.Post;


import com.example.demo.domain.Post.DTO.PostDTO;
import com.example.demo.domain.Post.DTO.PostPopUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    PostDTO toDto(Post post);
    @Mapping(source = "postId", target = "postId")
    @Mapping(source = "categoryType", target = "categoryType")
    @Mapping(source = "postTitle", target = "postTitle")
    @Mapping(source = "immediateCase", target = "immediateCase")
    @Mapping(source = "reward", target = "reward")
    @Mapping(source = "userName", target = "userName")
    PostPopUpDto postToPostPopUpDto(Post post);

    List<PostDTO> postsToPostDTOs(List<Post> posts);
}
