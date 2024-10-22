/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 10:42:42
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 11:09:11
 */
package com.example.sharedbike.mapper;

import com.example.sharedbike.domin.BaseResponse;
import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.entity.Rider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RiderMapper {



    @Select("SELECT * FROM Rider WHERE userid = #{userid}")
    Rider getRiderById(int userid);

    @Insert("INSERT INTO Rider(userid,username, gender, password, phonenumber, avatar, birthday) VALUES(#{userid},#{username}, #{gender}, #{password}, #{phonenumber}, #{avatar}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    void saveRider(Rider rider);
    List<Rider> getAllRiders(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);

    @Delete("DELETE FROM Rider WHERE userid = #{id}")
    void deleteRider(int id);
   // @Select("SELECT * FROM Riders WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    List<Rider> searchRiders(@Param("keyword") String keyword, @Param("searchBy") String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
    @Select("SELECT COUNT(*) FROM Rider WHERE ${searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    int getsCount(@Param("keyword") String keyword, @Param("searchBy") String searchBy);
    @Select("SELECT  COUNT(*) FROM Rider")
    int getCount();
}
