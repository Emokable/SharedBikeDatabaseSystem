package com.example.sharedbike.entity;
import lombok.Data;

import java.util.Date;
@Data
public class Rider {
    private int userid;
    private String username;
    private Gender gender;
    private String password;
    private String phoneNumber;
    private String avatar;
    private Date birthday;

    // Getters and setters
}




