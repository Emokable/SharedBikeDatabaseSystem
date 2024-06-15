package com.example.sharedbike.Controller;

import com.example.sharedbike.service.RideRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        // Validate input dates
        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }

        // Get hot areas, hot times, and free bike areas
        List<Map.Entry<String, Integer>> hotAreas = rideRecordService.getHotRideAreas(startDate, endDate);
        List<Map<String, Object>> hotTimes = rideRecordService.getHotRideTimes(startDate, endDate);
        List<Map.Entry<String, Integer>> freeBikeAreas = rideRecordService.getFreeBikeAreas();

        // Log the hot times for debugging
        for (Map<String, Object> hotTime : hotTimes) {
            System.out.println("Hot time slot: " + hotTime.get("timeSlot"));
            System.out.println("Area needs: " + hotTime.get("areaNeeds"));
        }

        // Generate scheduling plans
        List<String> schedulingPlans = new ArrayList<>();

        for (Map.Entry<String, Integer> hotArea : hotAreas) {
            String hotAreaLocation = hotArea.getKey();
            int rideCount = hotArea.getValue();
            int freeBikesInHotArea = getFreeBikesInArea(freeBikeAreas, hotAreaLocation);
            int bikesNeeded = rideCount/2 - freeBikesInHotArea;
            System.out.println("rc "+ rideCount+"f "+freeBikesInHotArea+"bknd"+bikesNeeded+"\n" );

            if (bikesNeeded > 0) {
                boolean isScheduled = false;

                // 找到该热点区域中最需要单车的时间段
                String mostNeededTimeSlot = getMostNeededTimeSlot(hotTimes, hotAreaLocation);

                for (Map.Entry<String, Integer> freeArea : freeBikeAreas) {
                    String freeAreaLocation = freeArea.getKey();
                    int freeBikesAvailable = freeArea.getValue();

                    if (!freeAreaLocation.equals(hotAreaLocation) && freeBikesAvailable > 0) {
                        int bikesToMove = Math.min(freeBikesAvailable, bikesNeeded);

                        String plan = String.format("在%s时段将%d辆单车从%s运往%s，调度理由：根据历史数据，该时段%s区域的单车需求量较高，而%s区域的单车使用率较低。",
                                mostNeededTimeSlot, bikesToMove, freeAreaLocation, hotAreaLocation, hotAreaLocation, freeAreaLocation);
                        schedulingPlans.add(plan);

                        freeArea.setValue(freeBikesAvailable - bikesToMove); // Update the free bikes count
                        bikesNeeded -= bikesToMove;
                        if (bikesNeeded <= 0) {
                            isScheduled = true;
                            break;
                        }
                    }
                }

                if (!isScheduled) {
                    schedulingPlans.add(String.format("无法为热点区域%s安排足够的单车调度需求（需要%d辆单车），请检查其他区域的可用单车数量。",
                            hotAreaLocation, bikesNeeded));
                }
            }
        }

        return schedulingPlans;
    }


    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private int getFreeBikesInArea(List<Map.Entry<String, Integer>> freeBikeAreas, String area) {
        for (Map.Entry<String, Integer> entry : freeBikeAreas) {
            if (entry.getKey().equals(area)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    private String getMostNeededTimeSlot(List<Map<String, Object>> hotTimes, String area) {
        String mostNeededTimeSlot = "";
        int maxNeed = 0;
        for (Map<String, Object> hotTime : hotTimes) {
            String timeSlot = (String) hotTime.get("timeSlot");
            Map<String, Integer> areaNeeds = (Map<String, Integer>) hotTime.get("areaNeeds");
            if (areaNeeds.containsKey(area) && areaNeeds.get(area) > maxNeed) {
                maxNeed = areaNeeds.get(area);
                mostNeededTimeSlot = timeSlot;
            }
        }
        return mostNeededTimeSlot;
    }
}
