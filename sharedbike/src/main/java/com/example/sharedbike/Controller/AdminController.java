//package com.example.sharedbike.Controller;
//
//import com.example.sharedbike.entity.Admin;
//import com.example.sharedbike.mapper.AdminMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admins")
//public class AdminController {
//    @Autowired
//    private AdminMapper adminMapper;
//
//    @GetMapping
//    public List<Admin> getAllAdmins() {
//        return adminMapper.getAllAdmins();
//    }
//
//    @GetMapping("/{id}")
//    public Admin getAdminById(@PathVariable int id) {
//        return adminMapper.getAdminById(id);
//    }
//
//    @PostMapping
//    public void saveAdmin(@RequestBody Admin admin) {
//        adminMapper.saveAdmin(admin);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteAdmin(@PathVariable int id) {
//        adminMapper.deleteAdmin(id);
//    }
//}
