package com.example.sharedbike.entity;

import lombok.Data;
import java.util.Date;
@Data
public class RideRecord {
    private int orderid;
    private int bikeid;
    private int userid;
    private Date startTime;
    private float startLocationX;
    private float startLocationY;
    private Date endTime;
    private float endLocationX;
    private float endLocationY;
    private String track;

    // Getters and setters
}
