package com.example.sharedbike.Controller;

import com.example.sharedbike.entity.NoParkingZone;
import com.example.sharedbike.entity.Rider;
import com.example.sharedbike.mapper.NoParkingZoneMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noParkingZones")
public class NoParkingZoneController {
    @Autowired
    private NoParkingZoneMapper noParkingZoneMapper;
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping
    public List<NoParkingZone> getAllNoParkingZones(String sortColumn, String sortOrder,int limit,int offset) {
            return noParkingZoneMapper.getAllNoParkingZones(sortColumn,sortOrder,limit,offset);
        }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/count")
    public int getCount() {
        return noParkingZoneMapper.getCount();
    }
    @RequiresPermissions(value = {"read_only","data_modification","superuser"},logical= Logical.OR)
    @GetMapping("/{id}")
    public NoParkingZone getNoParkingZoneById(@PathVariable int id) {
        return noParkingZoneMapper.getNoParkingZoneById(id);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    @PostMapping
    public void saveNoParkingZone(@RequestBody NoParkingZone noParkingZone) {
        noParkingZoneMapper.saveNoParkingZone(noParkingZone);
    }
    @RequiresPermissions(value = {"data_modification","superuser"},logical= Logical.OR)
    public void deleteNoParkingZone(@PathVariable int id) {
        noParkingZoneMapper.deleteNoParkingZone(id);
    }
}
