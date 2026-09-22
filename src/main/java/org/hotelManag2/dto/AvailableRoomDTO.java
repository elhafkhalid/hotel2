package org.hotelManag2.dto;

import org.hotelManag2.model.enums.RoomType;

import java.math.BigDecimal;

public class AvailableRoomDTO {

    private String roomNumber;
    private RoomType type;
    private int capacity;
    private BigDecimal pricePerNight;

    public AvailableRoomDTO(String roomNumber,RoomType type,int capacity,BigDecimal price){
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }
}