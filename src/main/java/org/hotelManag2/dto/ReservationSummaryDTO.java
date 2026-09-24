package org.hotelManag2.dto;

import org.hotelManag2.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationSummaryDTO {
    private final String code;
    private final String roomNumber;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final int nights;
    private final int guests;
    private final BigDecimal total;
    private final ReservationStatus status;

    public ReservationSummaryDTO(String code, String roomNumber, LocalDate checkIn, LocalDate checkOut,
                                 int nights, int guests, BigDecimal total, ReservationStatus status) {
        this.code = code;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = nights;
        this.guests = guests;
        this.total = total;
        this.status = status;
    }


    public String getCode() {
        return code;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getNights() {
        return nights;
    }

    public int getGuests() {
        return guests;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}