package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.entity.RideRecord;
import com.example.sharedbike.entity.Rider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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
   // @Select("SELECT * FROM RideRecord WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    List<RideRecord> searchRideRecord(@Param("keyword") String keyword, @Param("searchBy") String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
    @Select("SELECT * FROM RideRecord WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    int getsCount(@Param("keyword") String keyword, @Param("searchBy") String searchBy);
    @Delete("DELETE FROM RideRecord WHERE orderid = #{id}")
    void deleteRideRecord(int id);
    @Select("SELECT  COUNT(*) FROM RideRecord")
    int getCount();

    @Select("SELECT DATE(startTime) AS date, AVG(TIMESTAMPDIFF(MINUTE, startTime, endTime)) AS avg_ride_time " +
            "FROM RideRecord GROUP BY DATE(startTime);")
    List<Map<String, Object>> getAverageRideTimePerDay();

    @Select("SELECT startLocationX, startLocationY, COUNT(*) AS ride_count " +
            "FROM RideRecord WHERE startTime BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY startLocationX, startLocationY ORDER BY ride_count DESC LIMIT 10;")
    List<Map<String, Object>> getHotRideAreas(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("SELECT HOUR(startTime) AS hour, COUNT(*) AS ride_count " +
            "FROM RideRecord WHERE startTime BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY HOUR(startTime) ORDER BY ride_count DESC;")
    List<Map<String, Object>> getHotRideTimes(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("SELECT bikeid, COUNT(*) AS ride_count " +
            "FROM RideRecord GROUP BY bikeid ORDER BY ride_count DESC LIMIT 10;")
    List<Map<String, Object>> getMostFrequentBikes();

    @Select("SELECT bikeid, COUNT(*) AS ride_count " +
            "FROM RideRecord GROUP BY bikeid ORDER BY ride_count ASC LIMIT 10;")
    List<Map<String, Object>> getLeastFrequentBikes();

    @Select("SELECT userid, COUNT(*) AS ride_count " +
            "FROM RideRecord GROUP BY userid ORDER BY ride_count DESC LIMIT 10;")
    List<Map<String, Object>> getMostFrequentRiders();
}
