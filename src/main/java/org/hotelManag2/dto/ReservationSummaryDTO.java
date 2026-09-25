package org.hotelManag2.dto;

import org.hotelManag2.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationSummaryDTO {
    private final String code;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final int guests;
    private final int nights;
    private final BigDecimal total;
    private final ReservationStatus status;
    private final String roomNumber;

    public ReservationSummaryDTO(String code, LocalDate checkIn, LocalDate checkOut,
                                 int guests ,int nights,BigDecimal total,String roomNumber,ReservationStatus status) {
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

    public BigDecimal getTotal() {
        return total;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}