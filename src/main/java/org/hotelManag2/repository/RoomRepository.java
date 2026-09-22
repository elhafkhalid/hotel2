package org.hotelManag2.repository;

import org.hotelManag2.model.Room;
import org.hotelManag2.model.enums.RoomStatus;

import java.util.List;

public interface RoomRepository {
    List<Room> findByStatus(RoomStatus status);
    List<Room> findAll();
    void save(Room room);
    Room findByNumber(String roomNumber);
    void update(Room room);
    void updateStatus(String roomNumber,RoomStatus newStatus);
}