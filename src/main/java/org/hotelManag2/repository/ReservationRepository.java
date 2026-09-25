package org.hotelManag2.repository;

import org.hotelManag2.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {
    boolean hasOverlap(String roomNumber, LocalDate checkIn,LocalDate checkOut);
    void save(Reservation reservation);
    List<Reservation> findByUser(UUID userId);
}