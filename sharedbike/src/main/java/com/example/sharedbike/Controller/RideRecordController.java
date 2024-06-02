//package com.example.sharedbike.Controller;
//
//import com.example.sharedbike.entity.RideRecord;
//import com.example.sharedbike.mapper.RideRecordMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/rideRecords")
//public class RideRecordController {
//    @Autowired
//    private RideRecordMapper rideRecordMapper;
//
//    @GetMapping
//    public List<RideRecord> getAllRideRecords() {
//        return rideRecordMapper.getAllRideRecords();
//    }
//
//    @GetMapping("/{id}")
//    public RideRecord getRideRecordById(@PathVariable int id) {
//        return rideRecordMapper.getRideRecordById(id);
//    }
//
//    @PostMapping
//    public void saveRideRecord(@RequestBody RideRecord rideRecord) {
//        rideRecordMapper.saveRideRecord(rideRecord);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteRideRecord(@PathVariable int id) {
//        rideRecordMapper.deleteRideRecord(id);
//    }
//}
