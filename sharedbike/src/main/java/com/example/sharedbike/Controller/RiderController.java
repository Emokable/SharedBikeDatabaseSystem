package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.Rider;
import com.example.sharedbike.mapper.RiderMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/riders")
public class RiderController {
    @Autowired
    private RiderMapper riderMapper;
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping
    public List<Rider> getAllRiders(@RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size,
                                    @RequestParam(required = false, defaultValue = "userid") String sortBy,
                                    @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        int offset = (page - 1) * size ;
        return riderMapper.getAllRiders(offset, size, sortBy, sortOrder);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/count")
    public int getCount() {
        return riderMapper.getCount();
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/search")
    public List<Rider> searchRiders(String keyword) {
        return riderMapper.searchRiders(keyword);
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/{id}")
    public Rider getRiderById(@PathVariable int id) {
        return riderMapper.getRiderById(id);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @PostMapping
    public void saveRider(@RequestBody Rider rider) {
        riderMapper.saveRider(rider);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @DeleteMapping("/{id}")
    public void deleteRider(@PathVariable int id) {
        riderMapper.deleteRider(id);
    }
}

