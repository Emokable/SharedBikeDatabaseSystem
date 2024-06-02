//package com.example.sharedbike.mapper;
//
//import com.example.sharedbike.entity.NoParkingZone;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface NoParkingZoneMapper {
//    @Select("SELECT * FROM NoParkingZone")
//    List<NoParkingZone> getAllNoParkingZones();
//
//    @Select("SELECT * FROM NoParkingZone WHERE zone_id = #{id}")
//    NoParkingZone getNoParkingZoneById(int id);
//
//    @Insert("INSERT INTO NoParkingZone(name, location) VALUES(#{name}, #{location})")
//    @Options(useGeneratedKeys = true, keyProperty = "zoneId")
//    void saveNoParkingZone(NoParkingZone noParkingZone);
//
//    @Delete("DELETE FROM NoParkingZone WHERE zone_id = #{id}")
//    void deleteNoParkingZone(int id);
//}
