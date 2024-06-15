package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.entity.RideRecord;
import com.example.sharedbike.entity.Rider;
import com.example.sharedbike.mapper.RideRecordMapper;
import com.example.sharedbike.service.RideRecordService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rideRecords")
public class RideRecordController {
    @Autowired
    private RideRecordMapper rideRecordMapper;
    @Autowired
    private RideRecordService rideRecordService;
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping
    public List<RideRecord> getAllRideRecords(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "orderid") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return rideRecordMapper.getAllRideRecords(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/count")
    public int getCount() {
        return rideRecordMapper.getCount();
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/counts")
    public int getsCount(@RequestParam String keyword, @RequestParam String searchBy) {
        return rideRecordMapper.getsCount(keyword,searchBy);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/search")
    public List<RideRecord> searchRideRecord(@RequestParam String keyword, @RequestParam String searchBy,@RequestParam int page,@RequestParam int size,@RequestParam String sortOrder ) {
        int offset = (page - 1) * size;
        return rideRecordMapper.searchRideRecord(keyword,searchBy,offset,size,sortOrder);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/{id}")
    public RideRecord getRideRecordById(@PathVariable int id) {
        return rideRecordMapper.getRideRecordById(id);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/userid/{userid}")
    public List<RideRecord> getRideRecordUserId(@PathVariable int userid) {
        return rideRecordMapper.getRideRecordUserId(userid);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/bikeid/{bikeid}")
    public List<RideRecord> getRideRecordBikeId(@PathVariable int bikeid) {
        return rideRecordMapper.getRideRecordBikeId(bikeid);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @PostMapping
    public void saveRideRecord(@RequestBody RideRecord rideRecord) {
        rideRecordMapper.saveRideRecord(rideRecord);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @DeleteMapping("/{id}")
    public void deleteRideRecord(@PathVariable int id) {
        rideRecordMapper.deleteRideRecord(id);
    }
    @RequiresPermissions(value = {"read_only", "data_modification", "superuser"}, logical = Logical.OR)
    @GetMapping("/averageRideTimePerDay")
    public List<Map<String, Object>> getAverageRideTimePerDay() {
        return rideRecordMapper.getAverageRideTimePerDay();
    }

//    @RequiresPermissions(value = {"read_only", "data_modification", "superuser"}, logical = Logical.OR)
//    @GetMapping("/hotRideAreas")
//    public List<Map<String, Object>> getHotRideAreas(
//            @RequestParam String startDate,
//            @RequestParam String endDate) {
//        return rideRecordMapper.getHotRideAreas(startDate, endDate);
//    }
    @GetMapping("/hotAreas")
    public List<Map.Entry<String, Integer>> getHotRideAreas(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return rideRecordService.getHotRideAreas(startDate, endDate);
    }
    @RequiresPermissions(value = {"read_only", "data_modification", "superuser"}, logical = Logical.OR)
    @GetMapping("/hotRideTimes")
    public List<Map<String, Object>> getHotRideTimes(@RequestParam String startDate,
                                                     @RequestParam String endDate) {
        return rideRecordMapper.getHotRideTimes(startDate, endDate);
    }


    @RequiresPermissions(value = {"read_only", "data_modification", "superuser"}, logical = Logical.OR)
    @GetMapping("/mostFrequentBikes")
    public List<Map<String, Object>> getMostFrequentBikes() {
        return rideRecordMapper.getMostFrequentBikes();
    }

    @RequiresPermissions(value = {"read_only", "data_modification", "superuser"}, logical = Logical.OR)
    @GetMapping("/leastFrequentBikes")
    public List<Map<String, Object>> getLeastFrequentBikes() {
        return rideRecordMapper.getLeastFrequentBikes();
    }


    @RequiresPermissions(value = {"read_only", "data_modification", "superuser"}, logical = Logical.OR)
    @GetMapping("/mostFrequentRiders")
    public List<Map<String, Object>> getMostFrequentRiders() {
        return rideRecordMapper.getMostFrequentRiders();
    }
}
