package org.hotelManag2.model;

import org.hotelManag2.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private String code;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int guests;
    private int nights;
    private BigDecimal total;
    private ReservationStatus status;
    private UUID userId;
    private String roomNumber;
    private LocalDate createdAt;

    public Reservation() {
    }

    public Reservation(UUID id, String code, LocalDate checkIn, LocalDate checkOut, int guests,
                       int nights, BigDecimal total, ReservationStatus status,
                       UUID userId, String roomNumber, LocalDate createdAt) {
        this.id = id;
        this.code = code;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.nights = nights;
        this.total = total;
        this.status = status;
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}