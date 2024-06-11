package com.example.sharedbike.service;


import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }
}

