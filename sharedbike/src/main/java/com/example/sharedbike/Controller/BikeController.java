package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.mapper.BikeMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {
    @Autowired
    private BikeMapper bikeMapper;
    @RequiresPermissions("read")
    @GetMapping("/bikes")
    public List<Bike> getAllBikes(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "bikeid") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return bikeMapper.getAllBikes(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions("read")
    @GetMapping("/search")
    public List<Bike> searchBikes(@RequestParam String keyword) {
        return bikeMapper.searchBikes(keyword);
    }
    @RequiresPermissions("read")
    @GetMapping("/location")
    public List<Bike> searchBikesByLocation(
            @RequestParam float startX,
            @RequestParam float startY,
            @RequestParam float endX,
            @RequestParam float endY) {
        return bikeMapper.searchBikesByLocation(startX, startY, endX, endY);
    }
    @RequiresPermissions("read")
    @GetMapping("/{id}")
    public Bike getBikeById(@PathVariable int id) {
        return bikeMapper.getBikeById(id);
    }
    @RequiresPermissions("write")
    @PostMapping
    public void saveBike(@RequestBody Bike bike) {
        bikeMapper.saveBike(bike);
    }
    @RequiresPermissions("delete")
    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable int id) {
        bikeMapper.deleteBike(id);
    }
    @RequiresPermissions("write")
    @PutMapping("/update")
    void updateBikestatus(@RequestParam int id, @RequestParam String Status){
        bikeMapper.updateBikestatus(id,Status);

    }



}
