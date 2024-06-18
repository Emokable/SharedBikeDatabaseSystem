package com.example.sharedbike.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
@Data
public class RideRecord {
    private int orderid;
    private int bikeid;
    private int userid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private float startLocationX;
    private float startLocationY;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private float endLocationX;
    private float endLocationY;
    private String track;

    // Getters and setters
}
