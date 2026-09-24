package org.hotelManag2.service;

import org.hotelManag2.dto.AvailableRoomDTO;
import org.hotelManag2.dto.RoomSearchCriteria;
import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.Room;
import org.hotelManag2.model.enums.RoomStatus;
import org.hotelManag2.model.enums.RoomType;
import org.hotelManag2.repository.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<Room> listAvailable(){
        return roomRepository.findByStatus(RoomStatus.AVAILABLE);
    }

    public List<Room> findAll(){
       return roomRepository.findAll();
    }

    public void createRoom(String roomNumber, RoomType type, int capacity, BigDecimal price){
        validateRoom(roomNumber,type,capacity,price);
        roomRepository.save(new Room(roomNumber,type,capacity,price,RoomStatus.AVAILABLE));
    }

    public void updateRoom(String roomNumber, RoomType type, int capacity, BigDecimal price){
        Room existing = roomRepository.findByNumber(roomNumber);
        if (existing == null) {
            throw new BusinessException("Chambre introuvable.");
        }
        validateRoom(roomNumber,type,capacity,price);
        roomRepository.update(new Room(roomNumber,type,capacity,price,existing.getStatus()));
    }

    public void toggleMaintenance(String roomNumber){
        Room existing = roomRepository.findByNumber(roomNumber);
        if (existing == null) {
            throw new BusinessException("Chambre introuvable.");
        }

        RoomStatus newStatus = existing.getStatus() == RoomStatus.AVAILABLE?
                RoomStatus.MAINTENANCE : RoomStatus.AVAILABLE;
        roomRepository.updateStatus(roomNumber,newStatus);
    }

    public List<AvailableRoomDTO> serchAvailable(RoomSearchCriteria criteria){
        validateSearch(criteria);
        return roomRepository.findAvailable(criteria);
    }


    public void validateSearch(RoomSearchCriteria criteria){
        if(criteria.getCheckIn() == null || criteria.getCheckOut() == null){
            throw new BusinessException("Les dates sont obligatoires");
        }

        if(criteria.getCheckIn().isBefore(LocalDate.now())){
            throw new BusinessException("La date d'arrivee ne peut pas etre dans le passe");
        }

        if(criteria.getCheckIn().isAfter(criteria.getCheckOut())){
            throw new BusinessException("La date de depart doit etre apres la date d'arrivee");
        }

        if(criteria.getGuests()<=0) {
            throw new BusinessException("Le nombre de voyageurs doit > 0");
        }
    }

    private void validateRoom(String roomNumber, RoomType type, int capacity, BigDecimal pricePerNight) {
        if (roomNumber == null || roomNumber.isBlank()) {
            throw new BusinessException("Numero de chambre requis.");
        }
        if (type == null) {
            throw new BusinessException("Type de chambre invalide.");
        }
        if (capacity <= 0) {
            throw new BusinessException("La capacite doit etre superieure a 0.");
        }
        if (pricePerNight == null || pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Le prix par nuit doit etre superieur a 0.");
        }
    }
}
