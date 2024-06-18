package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.RideRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface RideRecordMapper {
    @Select("SELECT * FROM RideRecord")
    List<RideRecord> returnall();
    List<RideRecord> getAllRideRecords(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    @Select("SELECT * FROM RideRecord WHERE orderid = #{id}")
    RideRecord getRideRecordById(int id);
    @Select("SELECT * FROM RideRecord WHERE bikeid = #{id}")
    List<RideRecord> getRideRecordBikeId(int bikeid);
    @Select("SELECT * FROM RideRecord WHERE userid = #{id}")
    List<RideRecord> getRideRecordUserId(int userid);
    @Insert("INSERT INTO RideRecord(orderid,bikeid, userid, startTime, startLocationX, startLocationY, endTime, endLocationX, endlocation" +
            "y, track) VALUES(#{orderid}, #{bikeid}, #{userid}, #{startTime}, #{startLocationX}, #{startLocationY}, #{endTime}, #{endLocationX}, #{endLocationY}, #{track})")
    @Options(useGeneratedKeys = true, keyProperty = "orderid")
    void saveRideRecord(RideRecord rideRecord);
   // @Select("SELECT * FROM RideRecord WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    List<RideRecord> searchRideRecord(@Param("keyword") String keyword, @Param("searchBy") String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
    @Select("SELECT COUNT(*) FROM RideRecord WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
    int getsCount(@Param("keyword") String keyword, @Param("searchBy") String searchBy);
    @Delete("DELETE FROM RideRecord WHERE orderid = #{id}")
    void deleteRideRecord(int id);
    @Select("SELECT  COUNT(*) FROM RideRecord")
    int getCount();

    @Select("SELECT DATE(startTime) AS date, AVG(TIMESTAMPDIFF(MINUTE, startTime, endTime)) AS avg_ride_time " +
            "FROM RideRecord GROUP BY DATE(startTime);")
    List<Map<String, Object>> getAverageRideTimePerDay();
    @Select("SELECT * FROM RideRecord WHERE startTime BETWEEN #{startDate} AND #{endDate}")
    List<RideRecord> getRideRecordsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

//    @Select("SELECT startLocationX, startLocationY, COUNT(*) AS ride_count " +
//            "FROM RideRecord WHERE startTime BETWEEN #{startDate} AND #{endDate} " +
//            "GROUP BY startLocationX, startLocationY ORDER BY ride_count DESC LIMIT 10;")
//    List<Map<String, Object>> getHotRideAreas(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Select("SELECT HOUR(startTime) AS hour, COUNT(*) AS ride_count " +
            "FROM RideRecord WHERE startTime BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY HOUR(startTime) ORDER BY ride_count DESC;")
    List<Map<String, Object>> getHotRideTimes(@Param("startDate") String startDate, @Param("endDate") String endDate);

//    @Select("SELECT bikeid, COUNT(*) AS ride_count " +
//            "FROM RideRecord GROUP BY bikeid ORDER BY ride_count DESC LIMIT 10;")
//    List<Map<String, Object>> getMostFrequentBikes();
//
//    @Select("SELECT bikeid, COUNT(*) AS ride_count " +
//            "FROM RideRecord GROUP BY bikeid ORDER BY ride_count ASC LIMIT 10;")
//    List<Map<String, Object>> getLeastFrequentBikes();

    @Select("SELECT r.bikeid, b.brand, b.releasedate,b.lastusetime, b.warrantyPeriod, b.LocationX, b.LocationY, b.status, COUNT(*) AS ride_count " +
            "FROM RideRecord r JOIN Bike b ON r.bikeid = b.bikeid " +
            "GROUP BY r.bikeid ORDER BY ride_count DESC, lastusetime desc LIMIT 10;")
    List<Map<String, Object>> getMostFrequentBikes();

    @Select("SELECT r.bikeid, b.brand, b.releasedate,b.lastusetime, b.warrantyPeriod, b.LocationX, b.LocationY, b.status, COUNT(*) AS ride_count " +
            "FROM RideRecord r JOIN Bike b ON r.bikeid = b.bikeid " +
            "GROUP BY r.bikeid ORDER BY ride_count ASC ,lastusetime asc LIMIT 10;")
    List<Map<String, Object>> getLeastFrequentBikes();


    //    @Select("SELECT userid, COUNT(*) AS ride_count " +
//            "FROM RideRecord GROUP BY userid ORDER BY ride_count DESC LIMIT 10;")
//    List<Map<String, Object>> getMostFrequentRiders();
@Select("SELECT r.userid, r.username, r.gender, r.avatar,r.phonenumber,r.birthday,COUNT(*) AS ride_count " +
        "FROM RideRecord rr JOIN Rider r ON rr.userid = r.userid " +
        "GROUP BY rr.userid ORDER BY ride_count DESC LIMIT 10;")
List<Map<String, Object>> getMostFrequentRiders();
    @Select("SELECT * FROM RideRecord WHERE startTime BETWEEN #{startDate} AND #{endDate}")
    List<RideRecord> RrcBtdate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
