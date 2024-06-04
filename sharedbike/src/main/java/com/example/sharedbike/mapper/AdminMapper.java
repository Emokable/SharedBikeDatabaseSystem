package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.Bike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM Admin WHERE admin_id = #{adminid}")
    Admin getAdminById(int adminid);
    @Select("SELECT * FROM Admin WHERE admin_id LIKE CONCAT('%', #{keyword}, '%') OR username LIKE CONCAT('%', #{keyword}, '%')OR phone_number LIKE CONCAT('%', #{keyword}, '%') OR gender LIKE CONCAT('%', #{keyword}, '%')")
    List<Admin> searchAdmins(@Param("keyword") String keyword);
    @Insert("INSERT INTO Admin(admin_id,privileges, username, gender, password, phone_number, avatar, birthday) VALUES(#{adminid},#{privileges}, #{username}, #{gender}, #{password}, #{phoneNumber}, #{avatar}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "adminId")
    void saveAdmin(Admin admin);

    List<Admin> getAllAdmins(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    @Delete("DELETE FROM Admin WHERE admin_id = #{admin_id}")
    void deleteAdmin(int admin_id);
}
