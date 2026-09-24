package org.hotelManag2.dto;

import org.hotelManag2.model.enums.RoomStatus;
import org.hotelManag2.model.enums.RoomType;

import java.math.BigDecimal;

public class AvailableRoomDTO {

    private String roomNumber;
    private RoomType type;
    private int capacity;
    private BigDecimal pricePerNight;
    private RoomStatus status;

    public AvailableRoomDTO(String roomNumber, RoomType type, int capacity, BigDecimal price, RoomStatus status){
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.pricePerNight = price;
        this.status = status;
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

    public RoomStatus getStatus() {
        return status;
    }
}
