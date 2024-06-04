package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.entity.RideRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RideRecordMapper {

    List<Bike> getAllRideRecords(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    @Select("SELECT * FROM RideRecord WHERE orderid = #{id}")
    RideRecord getRideRecordById(int id);
    @Select("SELECT * FROM RideRecord WHERE bikeid = #{id}")
    List<RideRecord> getRideRecordBikeId(int bikeid);
    @Select("SELECT * FROM RideRecord WHERE userid = #{id}")
    List<RideRecord> getRideRecordUserId(int userid);
    @Insert("INSERT INTO RideRecord(orderid,bikeid, userid, start_time, start_location_x, start_location_y, end_time, end_location_x, end_location_y, track) VALUES(#{orderid}, #{bikeid}, #{userid}, #{startTime}, #{startLocationX}, #{startLocationY}, #{endTime}, #{endLocationX}, #{endLocationY}, #{track})")
    @Options(useGeneratedKeys = true, keyProperty = "orderid")
    void saveRideRecord(RideRecord rideRecord);

    @Delete("DELETE FROM RideRecord WHERE orderid = #{id}")
    void deleteRideRecord(int id);
    @Select("SELECT * FROM RideRecord WHERE orderid LIKE CONCAT('%', #{keyword}, '%')" +
            " OR bikeid LIKE CONCAT('%', #{keyword}, '%') " +
            "OR userid LIKE CONCAT('%', #{keyword}, '%')" +
            "OR start_time LIKE CONCAT('%', #{keyword}, '%')" +
            "OR track LIKE CONCAT('%', #{keyword}, '%')")
    List<RideRecord> searchRideRecords(@Param("keyword") String keyword);
}
