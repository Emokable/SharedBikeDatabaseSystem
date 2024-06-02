package com.example.sharedbike.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
public class FileController {
    @PostMapping("/upload") //等价于RequestMapping(value="/upload",method=RequestMethod.POST)
    //昵称 头像
    public String up(String nickname, MultipartFile photo, HttpServletRequest request) throws IOException {
        //昵称
        System.out.println(nickname);
        //获取图片的原始名称
        System.out.println(photo.getOriginalFilename());
        //取文件类型
        System.out.println(photo.getContentType());
        //获取web服务器的运行目录，因为最终要把文件存储到web服务器上（运行在linux系统上）
        String path= request.getServletContext().getRealPath("/upload/");
        System.out.println(path);
        saveFile(photo,path);
        return "上传成功！";
    }

    public void saveFile(MultipartFile photo, String path) throws IOException {
        File dir= new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(path+photo.getOriginalFilename());
        photo.transferTo(file);

    }
}
