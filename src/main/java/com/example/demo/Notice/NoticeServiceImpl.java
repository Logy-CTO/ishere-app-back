package com.example.demo.Notice;

import com.example.demo.File.FtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;
    private final FtpService ftpService;
    private static final String UPLOAD_DIR = "uploads/";

    @Transactional
    public AdminNotice noticeUp(NoticeDTO noticeDTO, MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = System.currentTimeMillis() + "-" + fileName;
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
            noticeDTO.setImg_url("http://113.131.111.147/images/" + uniqueFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return noticeRepository.save(noticeMapper.ToNotice(noticeDTO));
    }
}
