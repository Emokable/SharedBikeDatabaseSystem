package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.RideRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RideRecordMapper {
    @Select("SELECT * FROM RideRecord")
    List<RideRecord> getAllRideRecords();

    @Select("SELECT * FROM RideRecord WHERE orderid = #{id}")
    RideRecord getRideRecordById(int id);

    @Insert("INSERT INTO RideRecord(orderid,bikeid, userid, start_time, start_location_x, start_location_y, end_time, end_location_x, end_location_y, track) VALUES(#{orderid}, #{bikeid}, #{userid}, #{startTime}, #{startLocationX}, #{startLocationY}, #{endTime}, #{endLocationX}, #{endLocationY}, #{track})")
    @Options(useGeneratedKeys = true, keyProperty = "orderid")
    void saveRideRecord(RideRecord rideRecord);

    @Delete("DELETE FROM RideRecord WHERE orderid = #{id}")
    void deleteRideRecord(int id);
}
