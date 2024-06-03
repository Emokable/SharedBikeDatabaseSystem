package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.NoParkingZone;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoParkingZoneMapper {
    @Select("SELECT * FROM NoParkingZone ORDER BY ${sortColumn} ${sortOrder} LIMIT #{limit} OFFSET #{offset}")
    List<NoParkingZone> getAllNoParkingZones(@Param("sortColumn") String sortColumn,
                                             @Param("sortOrder") String sortOrder,
                                             @Param("limit") int limit,
                                             @Param("offset") int offset);

    @Select("SELECT * FROM NoParkingZone WHERE zone_id = #{id}")
    NoParkingZone getNoParkingZoneById(int id);

    @Insert("INSERT INTO NoParkingZone(zone_id,name, location) VALUES(#{zone_id},#{name}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "zoneId")
    void saveNoParkingZone(NoParkingZone noParkingZone);

    @Delete("DELETE FROM NoParkingZone WHERE zone_id = #{id}")
    void deleteNoParkingZone(int id);


}
