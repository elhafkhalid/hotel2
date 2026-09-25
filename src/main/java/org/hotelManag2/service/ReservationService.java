package org.hotelManag2.service;

import org.hotelManag2.dto.ReservationSummaryDTO;
import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.Reservation;
import org.hotelManag2.model.Room;
import org.hotelManag2.model.enums.ReservationStatus;
import org.hotelManag2.model.enums.RoomStatus;
import org.hotelManag2.repository.ReservationRepository;
import org.hotelManag2.repository.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class ReservationService {
   private final RoomRepository roomRepository;
   private final ReservationRepository reservationRepository;
   private final PricingService pricingService;

   public ReservationService(RoomRepository roomRepository,ReservationRepository reservationRepository,PricingService pricingService){
       this.reservationRepository = reservationRepository;
       this.roomRepository = roomRepository;
       this.pricingService = pricingService;
   }


  public ReservationSummaryDTO createReservation(UUID userId, String roomNumber,int guests, LocalDate checkIn,LocalDate checkOut){
       validateDates(checkIn,checkOut,guests);
       Room room = roomRepository.findByNumber(roomNumber);

      if (room == null) {
          throw new BusinessException("Chambre introuvable.");
      }
      if (room.getStatus() != RoomStatus.AVAILABLE) {
          throw new BusinessException("Chambre indisponible.");
      }
      if (guests > room.getCapacity()) {
          throw new BusinessException("Capacite insuffisante pour " + guests + " voyageurs.");
      }
      if (reservationRepository.hasOverlap(roomNumber, checkIn, checkOut)) {
          throw new BusinessException("Chambre deja reservee sur cette periode.");
      }

      int nights = (int) ChronoUnit.DAYS.between(checkIn,checkOut);
      BigDecimal total =  pricingService.calculateTotal(room.getPricePerNight(),checkIn,checkOut);

      String code = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

      Reservation reservation = new Reservation(UUID.randomUUID(),code,checkIn,checkOut,guests,nights,total,
              ReservationStatus.CONFIRMED,userId,roomNumber,LocalDate.now());


      reservationRepository.save(reservation);

      return new ReservationSummaryDTO(code,checkIn,checkOut,guests,nights,total,roomNumber,ReservationStatus.CONFIRMED);
   }


    public List<Reservation> findByUser(UUID userId) {
        return reservationRepository.findByUser(userId);
    }


    private void validateDates(LocalDate checkIn, LocalDate checkOut, int guests) {
        if (checkIn == null || checkOut == null) {
            throw new BusinessException("Les dates sont obligatoires.");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new BusinessException("La date d'arrivee ne peut pas etre dans le passe.");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new BusinessException("La date de depart doit etre apres la date d'arrivee.");
        }
        if (guests <= 0) {
            throw new BusinessException("Le nombre de voyageurs doit etre superieur a 0.");
        }
    }
}