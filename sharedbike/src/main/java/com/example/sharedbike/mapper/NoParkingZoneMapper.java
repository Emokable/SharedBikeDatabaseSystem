/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 21:15:33
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-13 00:40:24
 */
package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.NoParkingZone;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoParkingZoneMapper {
   // @Select("SELECT zoneid name ST_ASGeoJSON(location) FROM NoParkingZone ORDER BY ${sortColumn} ${sortOrder} LIMIT #{limit} OFFSET #{offset}")
    List<NoParkingZone> getAllNoParkingZones(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    List<NoParkingZone> searchNoParkingZones(@Param("keyword") String keyword,@Param("searchBy") String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
    @Select("SELECT * FROM NoParkingZone WHERE zoneid = #{id}")
    NoParkingZone getNoParkingZoneById(int id);

    @Insert("INSERT INTO NoParkingZone(zoneid, name, location) VALUES(#{zoneid}, #{name}, ST_GeomFromGeoJSON(#{location}))")
    @Options(useGeneratedKeys = true, keyProperty = "zoneId")
    void saveNoParkingZone(NoParkingZone noParkingZone);

    @Delete("DELETE FROM NoParkingZone WHERE zoneid = #{id}")
    void deleteNoParkingZone(int id);
    @Select("SELECT  COUNT(*) FROM NoParkingZone")
    int getCount();
}
