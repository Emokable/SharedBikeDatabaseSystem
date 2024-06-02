//package com.example.sharedbike.Controller;
//
//import com.example.sharedbike.entity.Bike;
//import com.example.sharedbike.mapper.BikeMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/bikes")
//public class BikeController {
//    @Autowired
//    private BikeMapper bikeMapper;
//
//    @GetMapping
//    public List<Bike> getAllBikes() {
//        return bikeMapper.getAllBikes();
//    }
//
//    @GetMapping("/{id}")
//    public Bike getBikeById(@PathVariable int id) {
//        return bikeMapper.getBikeById(id);
//    }
//
//    @PostMapping
//    public void saveBike(@RequestBody Bike bike) {
//        bikeMapper.saveBike(bike);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteBike(@PathVariable int id) {
//        bikeMapper.deleteBike(id);
//    }
//}
