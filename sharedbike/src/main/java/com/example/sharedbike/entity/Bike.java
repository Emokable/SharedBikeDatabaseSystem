package com.example.sharedbike.entity;

import com.example.sharedbike.entity.Enum.Status;
import lombok.Data;

import java.util.Date;

@Data
public class Bike {
    private int bikeid;
    private String brand;
    private Date  releasedate;
    private Date  lastusetime;
    private int warrantyPeriod;
    private float LocationX;
    private float LocationY;
    private Status status;

    // Getters and setters
}
