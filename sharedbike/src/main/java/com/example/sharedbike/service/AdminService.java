package com.example.sharedbike.service;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin authenticate(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
}
