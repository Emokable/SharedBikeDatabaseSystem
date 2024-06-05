package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.entity.RideRecord;
import com.example.sharedbike.mapper.RideRecordMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rideRecords")
public class RideRecordController {
    @Autowired
    private RideRecordMapper rideRecordMapper;
    @RequiresPermissions("read")
    @GetMapping
    public List<Bike> getAllRideRecords(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "orderid") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return rideRecordMapper.getAllRideRecords(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions("read")
    @GetMapping("/search")
    public List<RideRecord> searchRideRecords(@RequestParam String keyword) {
        return rideRecordMapper.searchRideRecords(keyword);
    }
    @RequiresPermissions("read")
    @GetMapping("/{id}")
    public RideRecord getRideRecordById(@PathVariable int id) {
        return rideRecordMapper.getRideRecordById(id);
    }
    @RequiresPermissions("read")
    @GetMapping("/userid/{userid}")
    public List<RideRecord> getRideRecordUserId(@PathVariable int userid) {
        return rideRecordMapper.getRideRecordUserId(userid);
    }
    @RequiresPermissions("read")
    @GetMapping("/bikeid/{bikeid}")
    public List<RideRecord> getRideRecordBikeId(@PathVariable int bikeid) {
        return rideRecordMapper.getRideRecordBikeId(bikeid);
    }
    @RequiresPermissions("write")
    @PostMapping
    public void saveRideRecord(@RequestBody RideRecord rideRecord) {
        rideRecordMapper.saveRideRecord(rideRecord);
    }
    @RequiresPermissions("write")
    @DeleteMapping("/{id}")
    public void deleteRideRecord(@PathVariable int id) {
        rideRecordMapper.deleteRideRecord(id);
    }
}
