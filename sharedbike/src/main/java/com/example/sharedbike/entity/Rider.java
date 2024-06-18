package com.example.sharedbike.entity;
import com.example.sharedbike.entity.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class Rider {
    private int userid;
    private String username;
    private Gender gender;
    private String password;
    private String phonenumber;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    // Getters and setters
}




