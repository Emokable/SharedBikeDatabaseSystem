//package com.example.sharedbike.Controller;
//
//import com.example.sharedbike.entity.NoParkingZone;
//import com.example.sharedbike.mapper.NoParkingZoneMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/noParkingZones")
//public class NoParkingZoneController {
//    @Autowired
//    private NoParkingZoneMapper noParkingZoneMapper;
//
//    @GetMapping
//    public List<NoParkingZone> getAllNoParkingZones() {
//        return noParkingZoneMapper.getAllNoParkingZones();
//    }
//
//    @GetMapping("/{id}")
//    public NoParkingZone getNoParkingZoneById(@PathVariable int id) {
//        return noParkingZoneMapper.getNoParkingZoneById(id);
//    }
//
//    @PostMapping
//    public void saveNoParkingZone(@RequestBody NoParkingZone noParkingZone) {
//        noParkingZoneMapper.saveNoParkingZone(noParkingZone);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteNoParkingZone(@PathVariable int id) {
//        noParkingZoneMapper.deleteNoParkingZone(id);
//    }
//}
