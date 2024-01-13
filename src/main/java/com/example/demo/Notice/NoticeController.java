package com.example.demo.Notice;

import com.example.demo.File.FtpService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    public final NoticeService noticeService;
    public final FtpService ftpService;
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/submit_notice")
    public String noticeUp(NoticeDTO dto, @RequestPart("file") MultipartFile file) throws IOException {
        noticeService.noticeUp(dto, file);

        return "redirect:/adminNotice.html";
    }




    @GetMapping("/adminNotice")
    public String showUploadForm() {
        return "adminNotice.html";
    }

}
