package com.parking.application.classes;

import java.util.Date;
public class Token {
    private String tokenNumber;
    private Car carDetails;
    private Slot slotDetails;
    private Date tokenDate;
    private long checkInTime;
    private long checkOutTime;

    public Token(String tokeNumber, Slot slotDetails, Car carDetails){
        this.tokenNumber = tokeNumber;
        this.carDetails = carDetails;
        this.slotDetails = slotDetails;
        this.tokenDate = new Date();
        this.checkInTime = System.currentTimeMillis();
        return;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }
    public Slot getSlotDetails() {
        return slotDetails;
    }
    public Token updateCheckOutTime(){
        this.checkOutTime = System.currentTimeMillis();
        return this;
    }

    public Car getCarDetails() {
        return carDetails;
    }
}
