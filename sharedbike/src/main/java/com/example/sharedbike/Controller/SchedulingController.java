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
        List<Map.Entry<String, Integer>> freeBikeAreas = rideRecordService.getFreeBikeAreas();

        // 调度方案
        List<String> schedulingPlans = new ArrayList<>();

        for (Map.Entry<String, Integer> area : hotAreas) {
            String areaLocation = area.getKey();
            int bikesNeeded = area.getValue();

            boolean isScheduled = false;

            for (Map.Entry<String, Integer> freeArea : freeBikeAreas) {
                String freeAreaLocation = freeArea.getKey();
                int freeBikesAvailable = freeArea.getValue();

                if (freeBikesAvailable >= bikesNeeded) {
                    for (Map<String, Object> time : hotTimes) {
                        String timeSlot = (String) time.get("timeSlot");

                        String plan = String.format("在%s时段将%d辆单车从%s运往%s，调度理由：热点骑行区域和时间段。",
                                timeSlot, bikesNeeded, freeAreaLocation, areaLocation);
                        schedulingPlans.add(plan);
                    }
                    isScheduled = true;
                    break;
                }
            }

            if (!isScheduled) {
                schedulingPlans.add(String.format("无法为热点区域%s安排足够的单车调度需求（需要%d辆单车），请检查其他区域的可用单车数量。",
                        areaLocation, bikesNeeded));
            }
        }

        return schedulingPlans;
    }
}
