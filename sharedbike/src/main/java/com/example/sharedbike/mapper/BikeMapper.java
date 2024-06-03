package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Bike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BikeMapper {
    @Select("SELECT * FROM Bike")
    List<Bike> getAllBikes();

    @Select("SELECT * FROM Bike WHERE bikeid = #{id}")
    Bike getBikeById(int id);

    @Insert("INSERT INTO Bike(bikeid,brand, release_date, warranty_period, status) VALUES(#{bikeid},#{brand}, #{releaseDate}, #{warrantyPeriod}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "bikeid")
    void saveBike(Bike bike);

    @Delete("DELETE FROM Bike WHERE bikeid = #{id}")
    void deleteBike(int id);
}
