package com.example.sharedbike.service;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.mapper.BikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeService {

    @Autowired
    private BikeMapper bikeMapper;

    public List<Bike> getLockedBikesInNoParkingZone(String coordinates) {
        System.out.println(coordinates);
        List<float[]> polygon = PolygonUtil.parseCoordinates(coordinates);
        float[] boundingBox = getBoundingBox(polygon);
        float startX = boundingBox[0];
        float startY = boundingBox[1];
        float endX = boundingBox[2];
        float endY = boundingBox[3];

        List<Bike> allBikes = bikeMapper.searchBikesByApproxLocation(startX, startY, endX, endY);
        return allBikes.stream()
                .filter(bike -> PolygonUtil.isPointInPolygon(bike.getLocationX(), bike.getLocationY(), polygon))
                .collect(Collectors.toList());
    }

    private float[] getBoundingBox(List<float[]> polygon) {
        if (polygon.isEmpty()) {
            return new float[]{0, 0, 0, 0}; // 返回一个默认的边界框
        }
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
        for (float[] point : polygon) {
            if (point[0] < minX) minX = point[0];
            if (point[0] > maxX) maxX = point[0];
            if (point[1] < minY) minY = point[1];
            if (point[1] > maxY) maxY = point[1];
        }
        return new float[]{minX, minY, maxX, maxY};
    }

}

