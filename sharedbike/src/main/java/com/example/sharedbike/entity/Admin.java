package com.example.sharedbike.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Admin {
    private int admin_id;
    private Privileges privileges;
    private String username;
    private Gender gender;
    private String password;
    private String phoneNumber;
    private String avatar;
    private Date birthday;

    // Getters and setters
}

