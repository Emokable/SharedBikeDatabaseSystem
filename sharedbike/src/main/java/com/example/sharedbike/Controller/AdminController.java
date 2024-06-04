package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    @GetMapping
    public List<Admin> getAllAdmins( @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "10") int size,
                                     @RequestParam(required = false, defaultValue = "admin_id") String sortBy,
                                     @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return adminMapper.getAllAdmins(offset, size, sortBy, sortOrder);
    }
    @GetMapping("/search")
    public List<Admin> searchAdmins(String keyword) {
        return adminMapper.searchAdmins(keyword);
    }


    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return adminMapper.getAdminById(id);
    }

    @PostMapping("/insert")
    public void insertAdmin(@RequestBody Admin admin) {
        adminMapper.insertAdmin(admin);
    }
    @PutMapping("/update")
    public void updateAdmin(@RequestBody Admin admin) {
        adminMapper.updateAdmin(admin);
    }
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable int id) {
        adminMapper.deleteAdmin(id);
    }
}
