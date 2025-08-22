package com.otd.onetoday_back.community.common.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${constants.file.directory}")
    private String baseDir;

    public String savePostFile(int postId, int memberNo, MultipartFile file) throws IOException {

        // 1) 확장자
        String ext = Optional.ofNullable(file.getOriginalFilename())
                .filter(fn -> fn.contains("."))
                .map(fn -> fn.substring(fn.lastIndexOf(".")))
                .orElse("");

        // 2) 베이스 경로를 절대경로로 강제
        Path base = Paths.get(baseDir);
        if (!base.isAbsolute()) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win") && (baseDir.startsWith("/") || baseDir.startsWith("\\"))) {
                // 윈도우에서 "/home/..." 같은 입력이 오면 C: 접두 보정
                base = Paths.get("C:" + baseDir);
            } else {
                base = base.toAbsolutePath();
            }
        }

        // 3) 최종 디렉토리: {base}/community/{postId}
        Path dir = base.resolve("community").resolve(String.valueOf(postId));
        Files.createDirectories(dir);

        // 4) 파일명/타깃
        String savedName = UUID.randomUUID() + ext;
        Path target = dir.resolve(savedName);

        // 5) 저장 — 반드시 Path 오버로드 사용 (Tomcat Part.write 우회)
        file.transferTo(target);

        // 6) 정적 리소스 URL 반환 (WebMvcConfig에서 /files/community/** 매핑 가정)
        return "/files/community/" + postId + "/" + savedName;
    }

    public String detectContentType(MultipartFile file) {
        return Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");
    }
}
