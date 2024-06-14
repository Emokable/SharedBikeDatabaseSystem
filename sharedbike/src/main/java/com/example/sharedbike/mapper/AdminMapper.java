package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.Bike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM Admin WHERE admin_id = #{admin_id}")
    Admin getAdminById(int admin_id);
   // @Select("SELECT * FROM Admins WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    List<Bike> searchAdmins(@Param("keyword") String keyword,@Param("searchBy")String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
    @Select("SELECT COUNT(*) FROM Admins WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    int getsCount(@Param("keyword") String keyword,@Param("searchBy")String searchBy);
    void insertAdmin(Admin admin);
    List<Admin> getAllAdmins(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    @Delete("DELETE FROM Admin WHERE admin_id = #{admin_id}")
    void deleteAdmin(int admin_id);
    void updateAdmin(Admin admin);
    void updateAdmin2(Admin admin);
    @Select("SELECT * FROM Admin WHERE username = #{username}")
    Admin findByUsername(String username);
    @Select("SELECT  COUNT(*) FROM Admin")
    int getCount();
}
