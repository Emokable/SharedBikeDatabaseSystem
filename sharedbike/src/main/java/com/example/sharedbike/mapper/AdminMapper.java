package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM Admin")
    List<Admin> getAllAdmins();

    @Select("SELECT * FROM Admin WHERE admin_id = #{admin_id}")
    Admin getAdminById(int admin_id);

    @Insert("INSERT INTO Admin(admin_id,privileges, username, gender, password, phone_number, avatar, birthday) VALUES(#{admin_id},#{privileges}, #{username}, #{gender}, #{password}, #{phoneNumber}, #{avatar}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "adminId")
    void saveAdmin(Admin admin);

    @Delete("DELETE FROM Admin WHERE admin_id = #{admin_id}")
    void deleteAdmin(int admin_id);
}
