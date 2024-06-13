package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Admin;
import com.example.sharedbike.entity.Rider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RiderMapper {



    @Select("SELECT * FROM Rider WHERE userid = #{userid}")
    Rider getRiderById(int userid);

    @Insert("INSERT INTO Rider(userid,username, gender, password, phone_number, avatar, birthday) VALUES(#{userid},#{username}, #{gender}, #{password}, #{phoneNumber}, #{avatar}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    void saveRider(Rider rider);
    List<Rider> getAllRiders(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);

    @Delete("DELETE FROM Rider WHERE userid = #{id}")
    void deleteRider(int id);

    @Select("<script>" +
            "SELECT * FROM Rider " +
            "WHERE 1=1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (userid LIKE CONCAT('%', #{keyword}, '%') " +
            "OR username LIKE CONCAT('%', #{keyword}, '%') " +
            "OR gender LIKE CONCAT('%', #{keyword}, '%') " +
            "OR phone_number LIKE CONCAT('%', #{keyword}, '%') " +
            "OR avatar LIKE CONCAT('%', #{keyword}, '%') " +
            "OR birthday LIKE CONCAT('%', #{keyword}, '%'))" +
            "</if>" +
            "</script>")    List<Rider> searchRiders(@Param("keyword") String keyword);
    @Select("SELECT  COUNT(*) FROM Rider")
    int getCount();
}
