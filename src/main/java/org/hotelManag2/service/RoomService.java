package org.hotelManag2.service;

import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.Room;
import org.hotelManag2.model.enums.RoomStatus;
import org.hotelManag2.model.enums.RoomType;
import org.hotelManag2.repository.RoomRepository;

import java.math.BigDecimal;
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
