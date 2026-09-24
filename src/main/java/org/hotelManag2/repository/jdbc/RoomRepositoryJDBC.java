package org.hotelManag2.repository.jdbc;

import org.hotelManag2.db.DatabaseConnection;
import org.hotelManag2.dto.AvailableRoomDTO;
import org.hotelManag2.dto.RoomSearchCriteria;
import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.Room;
import org.hotelManag2.model.enums.RoomStatus;
import org.hotelManag2.model.enums.RoomType;
import org.hotelManag2.repository.RoomRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryJDBC implements RoomRepository {
    public List<Room> findByStatus(RoomStatus status){

        String sql = "SELECT room_number, type,capacity,price_per_night,status FROM rooms WHERE status = ?";
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){
           ps.setString(1,status.name());

           try(ResultSet rs = ps.executeQuery()) {

               List<Room> rooms = new ArrayList<>();
               while(rs.next()){
                   rooms.add(mapRoom(rs));
               }
               return rooms;
           }
        }catch(SQLException e){
            throw new BusinessException("Recherche des chambres echouee : " + e.getMessage());
        }
    }

    public void save(Room room){
       String sql = "INSERT INTO rooms (room_number,type,capacity,price_per_night,status) VALUES (?,?,?,?,?)";
       Connection connection = DatabaseConnection.getInstance().getConnection();

       try (PreparedStatement ps = connection.prepareStatement(sql)) {
           ps.setString(1,room.getRoomNumber());
           ps.setString(2,room.getType().name());
           ps.setInt(3,room.getCapacity());
           ps.setBigDecimal(4,room.getPricePerNight());
           ps.setString(5,room.getStatus().name());

           ps.executeUpdate();
       }catch(SQLException e){
           throw new BusinessException("Enregistrement de la chambre echoue : " + e.getMessage());
       }
    }

    public void update(Room room){
        String sql = "UPDATE rooms SET type = ?, capacity = ? ,price_per_night = ? WHERE room_number = ?";
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1,room.getType().name());
            ps.setInt(2,room.getCapacity());
            ps.setBigDecimal(3,room.getPricePerNight());
            ps.setString(4,room.getRoomNumber());

            ps.executeUpdate();


        }catch(SQLException e){
            throw new BusinessException("Modification de la chambre echouee : " + e.getMessage());
        }
    }

    public void updateStatus(String roomNumber,RoomStatus newStatus){
        String sql = "UPDATE rooms SET status = ? WHERE room_number = ?";
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try(PreparedStatement ps = connection.prepareStatement(sql)){

         ps.setString(1,newStatus.name());
         ps.setString(2,roomNumber);

         ps.executeUpdate();

        }catch(SQLException e){
            throw new BusinessException("Mise a jour du statut echouee : " + e.getMessage());
        }

    }

    public List<Room> findAll(){
        String sql ="SELECT room_number,type,capacity,price_per_night,status FROM rooms ORDER BY room_number";
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
             try (ResultSet rs = ps.executeQuery()) {
                 List<Room> rooms = new ArrayList<>();
                 while(rs.next()){
                     rooms.add(mapRoom(rs));
                 }
                 return rooms;
             }


        }catch(SQLException e){
            throw new BusinessException("Recuperation des chambres echouee : " + e.getMessage());
        }
    }


    public Room findByNumber(String roomNumber){
        String sql = "SELECT room_number,type,capacity,price_per_night,status FROM rooms WHERE room_number = ?";
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1,roomNumber);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()) return mapRoom(rs);

                return null;
            }

        }catch(SQLException e){
            throw new BusinessException("Recherche de la chambre echouee : " + e.getMessage());
        }
    }

    public List<AvailableRoomDTO> findAvailable(RoomSearchCriteria criteria){
        String sql = "SELECT r.room_number, r.type, r.capacity, r.price_per_night, r.status "
                + "FROM rooms r "
                + "WHERE r.status = 'AVAILABLE' "
                + "AND r.capacity >= ? "
                + "AND NOT EXISTS ("
                + "SELECT 1 FROM reservations res "
                + "WHERE res.room_number = r.room_number "
                + "AND res.status = 'CONFIRMED' "
                + "AND res.check_in < ? AND res.check_out > ? "
                +")";

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1,criteria.getGuests());
            ps.setObject(2,criteria.getCheckOut());
            ps.setObject(3,criteria.getCheckIn());

            try(ResultSet rs = ps.executeQuery()) {
                List<AvailableRoomDTO> rooms = new ArrayList<>();
                while(rs.next()){
                    rooms.add(new AvailableRoomDTO(
                            rs.getString("room_number"),
                            RoomType.valueOf(rs.getString("type")),
                            rs.getInt("capacity"),
                            rs.getBigDecimal("price_per_night"),
                            RoomStatus.valueOf(rs.getString("status"))));
                }
                return rooms;
            }
        }catch(SQLException e){
            throw new BusinessException("Recherche de disponibilite echouee : " + e.getMessage());

        }
    }


    private Room mapRoom(ResultSet rs) throws SQLException {
        return new Room(
                rs.getString("room_number"),
                RoomType.valueOf(rs.getString("type")),
                rs.getInt("capacity"),
                rs.getBigDecimal("price_per_night"),
                RoomStatus.valueOf(rs.getString("status"))
        );
    }


}