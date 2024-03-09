package com.example.demo.domain.Image;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FtpService {
    @Value("${ftp.server}")
    private String SERVER;

    @Value("${ftp.url}")
    private String URL;

    @Value("${ftp.port}")
    private int PORT;

    @Value("${ftp.username}")
    private String USERNAME;

    @Value("${ftp.password}")
    private String PASSWORD;


    public void uploadFile(String localFilePath, String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(SERVER, PORT); // FTP 서버에 연결
            ftpClient.login(USERNAME, PASSWORD); // FTP 서버에 로그인

            ftpClient.enterLocalPassiveMode(); // 패시브 모드로 설정
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 파일 타입을 바이너리로 설정

            File localFile = new File(localFilePath); // 로컬 파일 객체 생성
            FileInputStream inputStream = new FileInputStream(localFile); // 파일을 읽어오는 스트림 생성

            // FTP 서버에 파일 업로드
            boolean uploaded = ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

            if (uploaded) {
                System.out.println("파일이 성공적으로 전송되었습니다.");
                // 파일 권한 변경 (예: 644 - 소유자 읽기/쓰기, 그룹과 다른 사용자 읽기 권한)
                ftpClient.sendSiteCommand("CHMOD 644 " + remoteFilePath);
            } else {
                System.out.println("파일 전송에 실패하였습니다.");
            }

            ftpClient.logout(); // FTP 서버에서 로그아웃
            ftpClient.disconnect(); // FTP 서버와의 연결 해제
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    public String setUrl(){ //정보 숨기기 위한 단순한 함수
        return URL;
    }
}
