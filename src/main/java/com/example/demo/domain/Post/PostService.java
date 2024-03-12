package com.example.demo.domain.Post;

import com.example.demo.domain.Image.FtpService;
import com.example.demo.domain.Image.ImageRepository;
import com.example.demo.domain.Image.ImageUploadDTO;
import com.example.demo.domain.Post.DTO.PostDTO;
import com.example.demo.domain.Post.DTO.PostPopUpDto;
import com.example.demo.domain.Image.PostImage;
import com.example.demo.domain.Post.DTO.PostUpdateDTO;
import com.example.demo.domain.Post.LocationFind.LocationFind;
import com.example.demo.domain.Post.LocationFind.LocationFindRepository;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Arrays;

import static com.example.demo.domain.Post.DTO.PostDTO.fromEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String UPLOAD_DIR = "uploads/";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final LocationFindRepository locationFindRepository;
    private final ImageRepository imageRepository;
    private final FtpService ftpService;
    @Autowired
    private EntityManager entityManager;

    //글쓰기
    @Transactional
    public PostDTO writePost(PostDTO postDto, List<MultipartFile> files) {
        Post post = postDto.toWrite();
        postRepository.save(post);
        PostDTO postDTO =  PostDTO.fromEntity(post, null);
        //이미지 업로드
        if(files != null && !files.isEmpty()) {
            List<String> imagesURL = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uniqueFileName = System.currentTimeMillis() + "-" + fileName;
                System.out.println(uniqueFileName);

                try {
                    // 파일을 업로드할 디렉토리 경로 설정
                    Path uploadPath = Path.of(UPLOAD_DIR);

                    // 디렉토리가 없으면 생성
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // 파일을 지정한 디렉토리로 복사
                    Path filePath = uploadPath.resolve(uniqueFileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    // FTP 서버에 파일 업로드 ** 중요 "/home/www/html/images/" 우리 이미지 경로
                    ftpService.uploadFile(filePath.toString(), "/home/www/html/images/" + uniqueFileName);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                PostImage image = PostImage.builder()
                        .image_name(uniqueFileName)
                        .img_url(ftpService.setUrl() + uniqueFileName)  // 민감정보 숨기기
                        .build();
                imagesURL.add(image.getImg_url());
                log.info("image ={}", image);
                imageRepository.save(image);
              post.addPostImage(image);
            }

            postDTO = PostDTO.fromEntity(post, imagesURL);
        }
        // Entity를 DTO로 변환하여 반환
        return postDTO;
    }
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    //postid로 여러 게시글 불러오기 메서드
    public List<PostDTO> getPost(List<Integer> postIds) {
        List<Post> posts = postRepository.findByPostIdIn(postIds);
        return postMapper.postsToPostDTOs(posts);
    }

    //","스플릿해서 user의 관심게시글 가져오기
    public List<String> getUserInterestPosts(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return Arrays.asList(user.getInterestPost().split(","));
    }

    //마이페이지 본인이 작성한 게시글 조회
    public List<PostDTO> getPostsByUserName(String userName) {
        List<Post> posts = postRepository.findByUserName(userName);
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = postMapper.toDto(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }


    public Post findById(int postId) throws Exception {
        return postRepository.findById(postId)
                .orElseThrow(() -> new Exception("해당 게시글을 찾을 수 없습니다. id=" + postId));
    }
    //게시글 수정(올바른 수정내역을 위하여 엔티티 반환)
    @Transactional
    public Post updatePost(int postId, PostUpdateDTO postUpdateDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + postId));

        post.updatePostFromDto(postUpdateDTO);
        Post updatedPost = postRepository.save(post);
        return updatedPost;
    }
    //게시글 삭제
    //delete는 반환값이 없으므로 void를 사용.
    @Transactional
    public void deletePost(int postId) throws Exception {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception("해당 게시글을 찾을 수 없습니다. id=" + postId));

        postRepository.delete(post);
    }
    //post의 DTO를 반환(필요한 정보만)
    public PostPopUpDto getPostPopUp(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + postId));

        // 주입받은 매퍼를 사용하여 변환
        return postMapper.postToPostPopUpDto(post);
    }
}