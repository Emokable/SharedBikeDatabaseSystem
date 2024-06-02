//package com.example.sharedbike.Controller;
//
//import com.example.sharedbike.entity.Rider;
//import com.example.sharedbike.mapper.RiderMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/riders")
//public class RiderController {
//    @Autowired
//    private RiderMapper riderMapper;
//
//    @GetMapping
//    public List<Rider> getAllRiders() {
//        return riderMapper.getAllRiders();
//    }
//
//    @GetMapping("/{id}")
//    public Rider getRiderById(@PathVariable int id) {
//        return riderMapper.getRiderById(id);
//    }
//
//    @PostMapping
//    public void saveRider(@RequestBody Rider rider) {
//        riderMapper.saveRider(rider);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteRider(@PathVariable int id) {
//        riderMapper.deleteRider(id);
//    }
//}
//
