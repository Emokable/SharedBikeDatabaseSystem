package com.example.sharedbike.service;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.entity.RideRecord;
import com.example.sharedbike.mapper.BikeMapper;
import com.example.sharedbike.mapper.RideRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RideRecordService {

    @Autowired
    private RideRecordMapper rideRecordMapper;

    @Autowired
    private BikeMapper bikeMapper;

    private static final int FREE_AREA_THRESHOLD = 15; // 设定空闲区域的阈值

    // 获取热点骑行区域及其骑行次数
    public List<Map.Entry<String, Integer>> getHotRideAreas(String startDate, String endDate) {
        List<RideRecord> records = rideRecordMapper.getRideRecordsBetweenDates(parseDate(startDate), parseDate(endDate));
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

    // 获取热点骑行时间段及其区域需求
    public List<Map<String, Object>> getHotRideTimes(String startDate, String endDate) {
        List<RideRecord> records = rideRecordMapper.getRideRecordsBetweenDates(parseDate(startDate), parseDate(endDate));
        Map<String, Integer> timeCount = new HashMap<>();
        Map<String, Map<String, Integer>> areaNeedsMap = new HashMap<>();

        for (RideRecord record : records) {
            String startTime = getTimeSlot(record.getStartTime());
            timeCount.put(startTime, timeCount.getOrDefault(startTime, 0) + 1);

            String endArea = getArea(record.getEndLocationX(), record.getEndLocationY());
            areaNeedsMap.putIfAbsent(startTime, new HashMap<>());
            Map<String, Integer> areaNeeds = areaNeedsMap.get(startTime);
            areaNeeds.put(endArea, areaNeeds.getOrDefault(endArea, 0) + 1);
        }

        // 将时间段按骑行次数排序并获取前十的时间段
        return timeCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("timeSlot", entry.getKey());
                    map.put("count", entry.getValue());
                    map.put("areaNeeds", areaNeedsMap.get(entry.getKey()));
                    return map;
                })
                .collect(Collectors.toList());
    }

    // 获取空闲单车较多的区域
    public List<Map.Entry<String, Integer>> getFreeBikeAreas() {
        List<Bike> availableBikes = bikeMapper.availableBike();
        Map<String, Integer> freeAreaCount = new HashMap<>();

        for (Bike bike : availableBikes) {
            String endArea = getArea(bike.getLocationX(), bike.getLocationY());
            freeAreaCount.put(endArea, freeAreaCount.getOrDefault(endArea, 0) + 1);
        }

        // 过滤出空闲单车数量超过阈值的区域并排序
        return freeAreaCount.entrySet().stream()
                .filter(entry -> entry.getValue() > FREE_AREA_THRESHOLD)
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    // 将经纬度转换为区域表示
    private String getArea(float x, float y) {
        return String.format("%.2f,%.2f", x, y);
    }

    // 将日期转换为时间段表示
    private String getTimeSlot(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return String.format("%02d:00-%02d:59", hour, hour);
    }

    // 获取时间段的开始时间
    private Date getStartTime(String timeSlot) {
        String[] times = timeSlot.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0].split(":")[0]));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    // 获取时间段的结束时间
    private Date getEndTime(String timeSlot) {
        String[] times = timeSlot.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[1].split(":")[0]));
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    // 将字符串日期解析为Date对象
    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("日期解析错误: " + dateString);
        }
    }
}
