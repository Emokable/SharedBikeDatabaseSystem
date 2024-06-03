package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Rider;
import com.example.sharedbike.mapper.RiderMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riders")
public class RiderController {
    @Autowired
    private RiderMapper riderMapper;

    @GetMapping
    public List<Rider> getAllRiders(String sortColumn, String sortOrder,int limit,int offset) {
        return riderMapper.getAllRiders(sortColumn,sortOrder,limit,offset);
    }
    @GetMapping("/search")
    public List<Rider> searchRiders(String keyword) {
        return riderMapper.searchRiders(keyword);
    }

    @GetMapping("/{id}")
    public Rider getRiderById(@PathVariable int id) {
        return riderMapper.getRiderById(id);
    }

    @PostMapping
    public void saveRider(@RequestBody Rider rider) {
        riderMapper.saveRider(rider);
    }

    @DeleteMapping("/{id}")
    public void deleteRider(@PathVariable int id) {
        riderMapper.deleteRider(id);
    }
}

