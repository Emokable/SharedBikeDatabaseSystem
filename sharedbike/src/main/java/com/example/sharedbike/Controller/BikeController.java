package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Bike;
import com.example.sharedbike.mapper.BikeMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {
    @Autowired
    private BikeMapper bikeMapper;
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/bikes")
    public List<Bike> getAllBikes(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "bikeid") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size;
        return bikeMapper.getAllBikes(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/count")
    public int getCount() {
        return bikeMapper.getCount();
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/counts")
    public int getsCount(@RequestParam String keyword,@RequestParam String searchBy) {
        return bikeMapper.getsCount(keyword,searchBy);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/search")
    public List<Bike> searchBikes(@RequestParam String keyword,@RequestParam String searchBy,@RequestParam int page,@RequestParam int size,@RequestParam String sortOrder ) {
        int offset = (page - 1) * size;
        return bikeMapper.searchBikes(keyword,searchBy,offset,size,sortOrder);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/location")
    public List<Bike> searchBikesByLocation(
            @RequestParam float startX,
            @RequestParam float startY,
            @RequestParam float endX,
            @RequestParam float endY) {
        return bikeMapper.searchBikesByLocation(startX, startY, endX, endY);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/{id}")
    public Bike getBikeById(@PathVariable int id) {
        return bikeMapper.getBikeById(id);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @PostMapping
    public void saveBike(@RequestBody Bike bike) {
        bikeMapper.saveBike(bike);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable int id) {
        bikeMapper.deleteBike(id);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @PutMapping("/update")
    void updateBikestatus(@RequestParam int id, @RequestParam String Status){
        bikeMapper.updateBikestatus(id,Status);

    }



}
