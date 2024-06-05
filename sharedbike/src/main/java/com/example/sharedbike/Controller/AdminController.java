package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.DTO.AdminUpdateDTO;
import com.example.sharedbike.mapper.AdminMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


@RestController
@RequestMapping("/admins")

public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @RequiresPermissions("read")
    @GetMapping
    public List<Admin> getAllAdmins( @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size,
                                     @RequestParam(required = false, defaultValue = "admin_id") String sortBy,
                                     @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return adminMapper.getAllAdmins(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions("read")
    @GetMapping("/search")
    public List<Admin> searchAdmins(String keyword) {
        return adminMapper.searchAdmins(keyword);
    }
    @RequiresPermissions("read")
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return adminMapper.getAdminById(id);
    }
    @RequiresPermissions("manage")
    @PostMapping("/insert")
    public void insertAdmin(@RequestBody Admin admin) {
        adminMapper.insertAdmin(admin);
    }
    @RequiresPermissions("manage")
    @PutMapping("/update")
    public void updateAdmin(@RequestBody Admin admin) {
        adminMapper.updateAdmin(admin);
    }
    @RequiresPermissions("read")
    @PutMapping("/update2")
    public void updateAdmin2(@RequestBody AdminUpdateDTO adminUpdateDTO) {
        // 获取当前用户的ID
        Subject currentUser = SecurityUtils.getSubject();
        Admin currentAdmin = (Admin) currentUser.getPrincipal();
        int currentUserId = currentAdmin.getAdminid();
        // 查询当前用户信息
        // 将允许修改的属性复制到当前用户对象中
        if (adminUpdateDTO.getGender() != null) {
            currentAdmin.setGender(adminUpdateDTO.getGender());
        }
        if (adminUpdateDTO.getPassword() != null) {
            currentAdmin.setPassword(adminUpdateDTO.getPassword());
        }
        if (adminUpdateDTO.getPhoneNumber() != null) {
            currentAdmin.setPhoneNumber(adminUpdateDTO.getPhoneNumber());
        }
        if (adminUpdateDTO.getAvatar() != null) {
            currentAdmin.setAvatar(adminUpdateDTO.getAvatar());
        }
        if (adminUpdateDTO.getBirthday() != null) {
            currentAdmin.setBirthday(adminUpdateDTO.getBirthday());
        }
        // 更新用户信息
        adminMapper.updateAdmin2(currentAdmin);
    }

    @RequiresPermissions("manage")
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable int id) {
        adminMapper.deleteAdmin(id);
    }
}
