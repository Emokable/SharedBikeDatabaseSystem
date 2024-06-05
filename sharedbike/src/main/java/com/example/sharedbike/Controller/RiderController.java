package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Rider;
import com.example.sharedbike.mapper.RiderMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riders")
public class RiderController {
    @Autowired
    private RiderMapper riderMapper;
    @RequiresPermissions("read")
    @GetMapping
    public List<Rider> getAllRiders(@RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size,
                                    @RequestParam(required = false, defaultValue = "userid") String sortBy,
                                    @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size ;
        return riderMapper.getAllRiders(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions("read")
    @GetMapping("/search")
    public List<Rider> searchRiders(String keyword) {
        return riderMapper.searchRiders(keyword);
    }
    @RequiresPermissions("read")
    @GetMapping("/{id}")
    public Rider getRiderById(@PathVariable int id) {
        return riderMapper.getRiderById(id);
    }
    @RequiresPermissions("write")
    @PostMapping
    public void saveRider(@RequestBody Rider rider) {
        riderMapper.saveRider(rider);
    }
    @RequiresPermissions("write")
    @DeleteMapping("/{id}")
    public void deleteRider(@PathVariable int id) {
        riderMapper.deleteRider(id);
    }
}

