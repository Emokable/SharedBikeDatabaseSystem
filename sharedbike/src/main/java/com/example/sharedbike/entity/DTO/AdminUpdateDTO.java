package com.example.sharedbike.entity.DTO;

import com.example.sharedbike.entity.Enum.Gender;
import lombok.Data;

import java.util.Date;
@Data
public class AdminUpdateDTO {
    private Gender gender;
    private String password;
    private String phonenumber;
    private String avatar;
    private Date birthday;

    // getters and setters
}
