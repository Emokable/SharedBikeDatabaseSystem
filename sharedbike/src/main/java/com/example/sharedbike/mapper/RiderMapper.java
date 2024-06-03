package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Rider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RiderMapper {
    @Select("SELECT * FROM Rider")
    List<Rider> getAllRiders();

    @Select("SELECT * FROM Rider WHERE userid = #{id}")
    Rider getRiderById(int id);

    @Insert("INSERT INTO Rider(userid,username, gender, password, phone_number, avatar, birthday) VALUES(#{userid},#{username}, #{gender}, #{password}, #{phoneNumber}, #{avatar}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    void saveRider(Rider rider);

    @Delete("DELETE FROM Rider WHERE userid = #{id}")
    void deleteRider(int id);
}
