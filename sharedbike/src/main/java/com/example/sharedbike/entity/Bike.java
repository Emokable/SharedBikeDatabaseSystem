package com.example.sharedbike.entity;

import com.example.sharedbike.entity.Enum.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Bike {
    private int bikeid;
    private String brand;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date  releasedate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date  lastusetime;
    private int warrantyPeriod;
    private float LocationX;
    private float LocationY;
    private Status status;

    // Getters and setters
}
