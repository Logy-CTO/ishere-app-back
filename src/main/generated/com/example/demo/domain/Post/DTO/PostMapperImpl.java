package com.example.demo.domain.Post.DTO;

import com.example.demo.domain.Post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-23T06:08:59+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.22 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setPostId( post.getPostId() );
        postDTO.setCategoryType( post.getCategoryType() );
        postDTO.setPostTitle( post.getPostTitle() );
        postDTO.setImmediateCase( post.getImmediateCase() );
        postDTO.setReward( post.getReward() );
        postDTO.setDescription( post.getDescription() );
        postDTO.setCreatedAt( post.getCreatedAt() );
        postDTO.setTransactionStatus( post.getTransactionStatus() );
        postDTO.setAreaName( post.getAreaName() );
        if ( post.getXLoc() != null ) {
            postDTO.setXLoc( post.getXLoc() );
        }
        if ( post.getYLoc() != null ) {
            postDTO.setYLoc( post.getYLoc() );
        }
        postDTO.setUserName( post.getUserName() );

        return postDTO;
    }

    @Override
    public PostPopUpDto postToPostPopUpDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostPopUpDto postPopUpDto = new PostPopUpDto();

        postPopUpDto.setPostId( post.getPostId() );
        postPopUpDto.setCategoryType( post.getCategoryType() );
        postPopUpDto.setPostTitle( post.getPostTitle() );
        postPopUpDto.setImmediateCase( post.getImmediateCase() );
        postPopUpDto.setReward( post.getReward() );
        postPopUpDto.setUserName( post.getUserName() );

        return postPopUpDto;
    }

    @Override
    public List<PostDTO> postsToPostDTOs(List<Post> posts) {
        if ( posts == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( posts.size() );
        for ( Post post : posts ) {
            list.add( toDto( post ) );
        }

        return list;
    }
}
