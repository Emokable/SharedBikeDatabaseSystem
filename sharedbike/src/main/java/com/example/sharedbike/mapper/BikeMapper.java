package com.example.sharedbike.mapper;
import com.example.sharedbike.entity.Bike;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BikeMapper {


        @Select("SELECT * FROM Bike WHERE bikeid = #{id}")
        Bike getBikeById(int id);

        @Insert("INSERT INTO Bike(bikeid, brand, releasedate, warrantyperiod, locationx, locationy, status) VALUES(#{bikeid}, #{brand}, #{releaseDate}, #{warrantyPeriod}, #{LocationX}, #{LocationY}, #{status})")
        @Options(useGeneratedKeys = true, keyProperty = "bikeid")
        void saveBike(Bike bike);

        @Delete("DELETE FROM Bike WHERE bikeid = #{id}")
        void deleteBike(int id);
        List<Bike> getAllBikes(@Param("offset") int offset, @Param("size") int size, @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);

      //  @Select("SELECT * FROM Bike WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
        List<Bike> searchBikes(@Param("keyword") String keyword,@Param("searchBy") String searchBy,@Param("offset") int offset, @Param("size") int size , @Param("sortOrder") String sortOrder);
         @Select("SELECT COUNT(*) FROM Bike WHERE #{searchBy} LIKE CONCAT('%', #{keyword}, '%') ")
         int getsCount(@Param("keyword") String keyword,@Param("searchBy")String searchBy);

        @Select("SELECT * FROM Bike WHERE locationx BETWEEN #{startX} AND #{endX} AND locationy BETWEEN #{startY} AND #{endY} AND status = 'available'")
        List<Bike> searchBikesByLocation(@Param("startX") float startX, @Param("startY") float startY, @Param("endX") float endX, @Param("endY") float endY);
        @Select("SELECT * FROM Bike WHERE status = 'available'")
         List<Bike>availableBike();

       // @Update("UPDATE Bike SET status = #{status} WHERE bikeid = #{id}")
        void updateBike(Bike bike);
        @Select("SELECT  COUNT(*) FROM Bike")
        int getCount();
         @Select("SELECT  * FROM Bike ORDER BY lastusetime asc limit 10")
         List<Bike> longtimenouse();
}
