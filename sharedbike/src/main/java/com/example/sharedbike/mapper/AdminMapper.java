package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.Bike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM Admin WHERE admin_id = #{admin_id}")
    Admin getAdminById(int admin_id);
    @Select("SELECT * FROM Admin WHERE admin_id LIKE CONCAT('%', #{keyword}, '%') OR username LIKE CONCAT('%', #{keyword}, '%')OR phone_number LIKE CONCAT('%', #{keyword}, '%') OR gender LIKE CONCAT('%', #{keyword}, '%')")
    List<Admin> searchAdmins(@Param("keyword") String keyword);
    void insertAdmin(Admin admin);
    List<Admin> getAllAdmins(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    @Delete("DELETE FROM Admin WHERE admin_id = #{admin_id}")
    void deleteAdmin(int admin_id);
    void updateAdmin(Admin admin);
    @Select("SELECT * FROM Admin WHERE username = #{username}")
    Admin findByUsername(String username);
}
