package org.hotelManag2.repository.jdbc;

import org.hotelManag2.db.DatabaseConnection;
import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.Reservation;
import org.hotelManag2.model.enums.ReservationStatus;
import org.hotelManag2.repository.ReservationRepository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservationRepositoryJDBC implements ReservationRepository {

    private static final String COLUMNS = "id, code, check_in, check_out, guests, nights, total, status, user_id, room_number, created_at";

    public void save(Reservation reservation){

        String sql = "INSERT INTO reservations " + "(" + COLUMNS + ")" + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setObject(1, reservation.getId());
            ps.setString(2, reservation.getCode());
            ps.setObject(3, reservation.getCheckIn());
            ps.setObject(4, reservation.getCheckOut());
            ps.setInt(5, reservation.getGuests());
            ps.setInt(6, reservation.getNights());
            ps.setBigDecimal(7, reservation.getTotal());
            ps.setString(8, reservation.getStatus().name());
            ps.setObject(9, reservation.getUserId());
            ps.setString(10, reservation.getRoomNumber());
            ps.setObject(11, reservation.getCreatedAt());

            ps.executeUpdate();

        }catch(SQLException e){
            throw new BusinessException("Verification de chevauchement echouee : " + e.getMessage());
        }

    }
    public boolean hasOverlap(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        String sql = "SELECT 1 FROM reservations " +
                "WHERE room_number = ? AND status = 'CONFIRMED' AND check_in < ? AND check_out > ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, roomNumber);
            ps.setObject(2, checkOut);
            ps.setObject(3, checkIn);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new BusinessException("Verification de chevauchement echouee : " + e.getMessage());
        }
    }


    public List<Reservation> findByUser(UUID userId) {
        String sql = "SELECT " + COLUMNS + " FROM reservations WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

             ps.setObject(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                List<Reservation> reservations = new ArrayList<>();
                while (rs.next()) {
                    reservations.add(mapReservation(rs));
                }
                return reservations;
            }
        } catch (SQLException e) {
            throw new BusinessException("Lecture des reservations echouee : " + e.getMessage());
        }
    }

    private Reservation mapReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getObject("id", UUID.class),
                rs.getString("code"),
                rs.getObject("check_in", LocalDate.class),
                rs.getObject("check_out", LocalDate.class),
                rs.getInt("guests"),
                rs.getInt("nights"),
                rs.getBigDecimal("total"),
                ReservationStatus.valueOf(rs.getString("status")),
                rs.getObject("user_id", UUID.class),
                rs.getString("room_number"),
                rs.getObject("created_at", LocalDate.class)
        );
    }


}