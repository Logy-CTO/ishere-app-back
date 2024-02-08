package com.example.demo.Notice;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NoticeService {
    public AdminNotice noticeUp(NoticeDTO noticeDTO, MultipartFile file) throws IOException;
}
