package com.example.sharedbike.entity;

import lombok.Data;

@Data
public class NoParkingZone {
    private int zone_id;
    private String name;
    private String location; // Adjust type based on your database's geometry support

    // Getters and setters
}

