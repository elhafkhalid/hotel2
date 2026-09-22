package org.hotelManag2.dto;

import java.time.LocalDate;

public class RoomSearchCriteria {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int guests;

    public RoomSearchCriteria(LocalDate checkIn,LocalDate checkOut,int guests){
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
    }


    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getGuests() {
        return guests;
    }
}