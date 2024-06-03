package com.example.sharedbike.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Bike {
    private int bikeid;
    private String brand;
    private Date releaseDate;
    private int warrantyPeriod;
    private Status status;

    // Getters and setters
}
