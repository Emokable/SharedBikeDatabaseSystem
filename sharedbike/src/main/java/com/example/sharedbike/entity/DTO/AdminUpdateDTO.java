package com.example.sharedbike.entity.DTO;

import com.example.sharedbike.entity.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class AdminUpdateDTO {
    private Gender gender;
    private String password;
    private String phonenumber;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    // getters and setters
}
