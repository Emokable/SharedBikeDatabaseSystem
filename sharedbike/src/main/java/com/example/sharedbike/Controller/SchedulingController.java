package com.example.sharedbike.Controller;

import com.example.sharedbike.service.RideRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

    @Autowired
    private RideRecordService rideRecordService;

    @GetMapping("/plan")
    public List<String> getSchedulingPlan(@RequestParam String startDate,
                                          @RequestParam String endDate) {
        List<Map.Entry<String, Integer>> hotAreas = rideRecordService.getHotRideAreas(startDate, endDate);
        List<Map<String, Object>> hotTimes = rideRecordService.getHotRideTimes(startDate, endDate);

        // 调度方案
        List<String> schedulingPlans = new ArrayList<>();

        // 示例调度方案，假设我们根据热点时间和区域来调度
        for (Map.Entry<String, Integer> area : hotAreas) {
            for (Map<String, Object> time : hotTimes) {
                String timeSlot = (String) time.get("timeSlot");
                String areaLocation = area.getKey();
                int bikesNeeded = area.getValue();

                String plan = String.format("在%s时段将%d辆单车从某地运往%s，调度理由：热点骑行区域和时间段。",
                        timeSlot, bikesNeeded, areaLocation);
                schedulingPlans.add(plan);
            }
        }

        return schedulingPlans;
    }
}

