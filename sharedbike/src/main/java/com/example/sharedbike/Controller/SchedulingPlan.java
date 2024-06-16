package com.example.sharedbike.Controller;


public class SchedulingPlan {
    private boolean isCompleted;
    private String startLocation;
    private String endLocation;
    private String timeSlot;
    private int bikesMoved;
    private String reason;

    public SchedulingPlan(boolean isCompleted, String startLocation, String endLocation, String timeSlot, int bikesMoved, String reason) {
        this.isCompleted = isCompleted;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.timeSlot = timeSlot;
        this.bikesMoved = bikesMoved;
        this.reason = reason;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getBikesMoved() {
        return bikesMoved;
    }

    public void setBikesMoved(int bikesMoved) {
        this.bikesMoved = bikesMoved;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

