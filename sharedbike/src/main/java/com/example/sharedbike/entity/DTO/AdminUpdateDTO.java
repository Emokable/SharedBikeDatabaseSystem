package com.example.sharedbike.entity.DTO;

import com.example.sharedbike.entity.Gender;
import lombok.Data;

import java.util.Date;
@Data
public class AdminUpdateDTO {
    private Gender gender;
    private String password;
    private String phoneNumber;
    private String avatar;
    private Date birthday;

    // getters and setters
}