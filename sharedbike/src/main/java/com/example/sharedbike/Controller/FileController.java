package com.example.sharedbike.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
public class FileController {

        @PostMapping("/upload")
        public String upload(String nickname, MultipartFile photo, HttpServletRequest request) throws IOException {
            String path = request.getServletContext().getRealPath("/upload/");
            saveFile(photo, path);
            return "上传成功";
        }

        public void saveFile(MultipartFile photo, String path) throws IOException {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(path + photo.getOriginalFilename());
            photo.transferTo(file);
        }


    }

