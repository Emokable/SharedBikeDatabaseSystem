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
    @Select("SELECT zone_id name ST_ASGeoJSON(location) FROM NoParkingZone ORDER BY ${sortColumn} ${sortOrder} LIMIT #{limit} OFFSET #{offset}")
    List<NoParkingZone> getAllNoParkingZones(@Param("sortColumn") String sortColumn,
                                             @Param("sortOrder") String sortOrder,
                                             @Param("limit") int limit,
                                             @Param("offset") int offset);

    @Select("SELECT * FROM NoParkingZone WHERE zone_id = #{id}")
    NoParkingZone getNoParkingZoneById(int id);

    @Insert("INSERT INTO NoParkingZone(zone_id, name, location) VALUES(#{zone_id}, #{name}, ST_GeomFromGeoJSON(#{location}))")
    @Options(useGeneratedKeys = true, keyProperty = "zoneId")
    void saveNoParkingZone(NoParkingZone noParkingZone);

    @Delete("DELETE FROM NoParkingZone WHERE zone_id = #{id}")
    void deleteNoParkingZone(int id);
    @Select("SELECT  COUNT(*) FROM NoParkingZone")
    int getCount();
}
