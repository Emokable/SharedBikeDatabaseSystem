package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.DTO.AdminUpdateDTO;
import com.example.sharedbike.mapper.AdminMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping
    public List<Admin> getAllAdmins( @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size,
                                     @RequestParam(required = false, defaultValue = "admin_id") String sortBy,
                                     @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return adminMapper.getAllAdmins(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/count")
    public int getCount() {
        return adminMapper.getCount();
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/search")
    public List<Admin> searchAdmins(String keyword) {
        return adminMapper.searchAdmins(keyword);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return adminMapper.getAdminById(id);
    }
    @RequiresPermissions("superuser")
    @PostMapping("/insert")
    public void insertAdmin(@RequestBody Admin admin) {
        adminMapper.insertAdmin(admin);
    }
    @RequiresPermissions("superuser")
    @PutMapping("/update")
    public void updateAdmin(@RequestBody Admin admin) {
        Subject currentUser = SecurityUtils.getSubject();
        Admin currentAdmin = (Admin) currentUser.getPrincipal();
        currentAdmin.setAdminid(admin.getAdminid());
        currentAdmin.setPrivileges(admin.getPrivileges());
        currentAdmin.setGender(admin.getGender());
        if (admin.getPassword() != null) {
            // 盐值
            ByteSource salt = ByteSource.Util.bytes(currentAdmin.getUsername());
            // 设置哈希算法和迭代次数
            String hashAlgorithmName = "SHA-1";
            int hashIterations = 16;
            // 使用 SimpleHash 进行加密
            SimpleHash hash = new SimpleHash(hashAlgorithmName, currentAdmin.getUsername(), salt, hashIterations);
            String encryptedPassword = hash.toHex();
            currentAdmin.setPassword(encryptedPassword);
        }
            currentAdmin.setPhoneNumber(admin.getPhoneNumber());
            currentAdmin.setAvatar(admin.getAvatar());
            currentAdmin.setBirthday(admin.getBirthday());
        adminMapper.updateAdmin(currentAdmin);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
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
            // 盐值
            ByteSource salt = ByteSource.Util.bytes(currentAdmin.getUsername());
            // 设置哈希算法和迭代次数
            String hashAlgorithmName = "SHA-1";
            int hashIterations = 16;
            // 使用 SimpleHash 进行加密
            SimpleHash hash = new SimpleHash(hashAlgorithmName, currentAdmin.getUsername(), salt, hashIterations);
            String encryptedPassword = hash.toHex();
            currentAdmin.setPassword(encryptedPassword);
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

    @RequiresPermissions("superuser")
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable int id) {
        adminMapper.deleteAdmin(id);
    }


}
