/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-17 20:23:28
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 07:02:53
 */
package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.Bike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM Admin WHERE adminid = #{adminid}")
    Admin getAdminById(int adminid);
   // @Select("SELECT * FROM Admins WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    List<Bike> searchAdmins(@Param("keyword") String keyword,@Param("searchBy")String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
    @Select("SELECT COUNT(*) FROM Admin WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    int getsCount(@Param("keyword") String keyword,@Param("searchBy")String searchBy);
    @Insert("INSERT INTO Admin (adminid, privileges, username, gender, password, phonenumber, avatar, birthday) VALUES (#{adminid}, #{privileges}, #{username}, #{gender}, #{password}, #{phonenumber}, #{avatar}, #{birthday})")
    void insertAdmin(Admin admin);
    List<Admin> getAllAdmins(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    @Delete("DELETE FROM Admin WHERE adminid = #{adminid}")
    void deleteAdmin(int adminid);
    void updateAdmin(Admin admin);
    void updateAdmin2(Admin admin);
    @Select("SELECT * FROM Admin WHERE username = #{username}")
    Admin findByUsername(String username);
    @Select("SELECT  COUNT(*) FROM Admin")
    int getCount();
   @Select("SELECT  max(adminid) FROM Admin")
   int maxid();
}
