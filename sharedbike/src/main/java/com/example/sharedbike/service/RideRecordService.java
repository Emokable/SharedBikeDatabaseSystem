package com.example.sharedbike.service;

import com.example.sharedbike.entity.RideRecord;
import com.example.sharedbike.mapper.RideRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class RideRecordService {
//
//    @Autowired
//    private RideRecordMapper rideRecordMapper;
//
//    public List<Map.Entry<String, Integer>> getHotRideAreas(String startDate, String endDate) {
//        List<RideRecord> records = rideRecordMapper.getRideRecordsBetweenDates(startDate, endDate);
//        Map<String, Integer> areaCount = new HashMap<>();
//
//        for (RideRecord record : records) {
//            String startArea = getArea(record.getStartLocationX(), record.getStartLocationY());
//            String endArea = getArea(record.getEndLocationX(), record.getEndLocationY());
//
//            areaCount.put(startArea, areaCount.getOrDefault(startArea, 0) + 1);
//            areaCount.put(endArea, areaCount.getOrDefault(endArea, 0) + 1);
//        }
//
//        // 对区域按照骑行次数进行排序并获取前十的区域
//        List<Map.Entry<String, Integer>> sortedAreas = areaCount.entrySet().stream()
//                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//                .limit(10)
//                .collect(Collectors.toList());
//
//        return sortedAreas;
//    }
//
//    private String getArea(float x, float y) {
//        // 取经纬度小数点后2位分类
//        return String.format("%.2f,%.2f", x, y);
//    }
//}
@Service
public class RideRecordService {

    @Autowired
    private RideRecordMapper rideRecordMapper;

    public List<Map.Entry<String, Integer>> getHotRideAreas(String startDate, String endDate) {
        List<RideRecord> records = rideRecordMapper.getRideRecordsBetweenDates(startDate, endDate);
        Map<String, Integer> areaCount = new HashMap<>();

        for (RideRecord record : records) {
            String startArea = getArea(record.getStartLocationX(), record.getStartLocationY());
            String endArea = getArea(record.getEndLocationX(), record.getEndLocationY());

            areaCount.put(startArea, areaCount.getOrDefault(startArea, 0) + 1);
            areaCount.put(endArea, areaCount.getOrDefault(endArea, 0) + 1);
        }

        // 对区域按照骑行次数进行排序并获取前十的区域
        return areaCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getHotRideTimes(String startDate, String endDate) {
        List<RideRecord> records = rideRecordMapper.getRideRecordsBetweenDates(startDate, endDate);
        Map<String, Integer> timeCount = new HashMap<>();

        for (RideRecord record : records) {
            String startTime = getTimeSlot(record.getStartTime());
            timeCount.put(startTime, timeCount.getOrDefault(startTime, 0) + 1);
        }

        // 将时间段按骑行次数排序并获取前十的时间段
        return timeCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("timeSlot", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private String getArea(float x, float y) {
        // 取经纬度小数点后2位分类
        return String.format("%.2f,%.2f", x, y);
    }

    private String getTimeSlot(Date date) {
        // 按小时划分时间段
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return String.format("%02d:00-%02d:59", hour, hour);
    }
}
