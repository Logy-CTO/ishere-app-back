package com.example.demo.domain.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
public class FileUploadController {

    // 업로드할 파일을 임시 저장할 디렉토리 경로
    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private FtpService ftpService;
    @PostMapping("/upload")
    public String uploadFile(@RequestPart("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        // 파일 이름 중복 방지를 위해 고유한 파일 이름 생성
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

            // 업로드된 파일의 경로를 리다이렉트 속성에 추가
            redirectAttributes.addFlashAttribute("message", "파일 업로드가 완료되었습니다.");
            redirectAttributes.addFlashAttribute("filePath", filePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/upload.html";
    }


    // 이거 없으면 405 Method에러 생김
    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload.html";
    }
}