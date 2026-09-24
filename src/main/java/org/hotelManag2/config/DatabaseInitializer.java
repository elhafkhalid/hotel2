package org.hotelManag2.config;

import org.hotelManag2.db.DatabaseConnection;
import org.hotelManag2.exception.BusinessException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class DatabaseInitializer {

    public static void initialize() {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        String[] drops = {
                "DROP TABLE IF EXISTS invoices CASCADE",
                "DROP TABLE IF EXISTS payments CASCADE",
                "DROP TABLE IF EXISTS reservations CASCADE",
                "DROP TABLE IF EXISTS rooms CASCADE",
                "DROP TABLE IF EXISTS users CASCADE"
        };

        String usersTable = "CREATE TABLE users (" +
                "id UUID PRIMARY KEY, " +
                "full_name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(150) NOT NULL UNIQUE, " +
                "password VARCHAR(64) NOT NULL, " +
                "role VARCHAR(20) NOT NULL)";

        String roomsTable = "CREATE TABLE rooms (" +
                "room_number VARCHAR(10) PRIMARY KEY, " +
                "type VARCHAR(20) NOT NULL, " +
                "capacity INT NOT NULL, " +
                "price_per_night NUMERIC(10,2) NOT NULL, " +
                "status VARCHAR(20) NOT NULL)";

        String reservationsTable = "CREATE TABLE reservations (" +
                "id UUID PRIMARY KEY, " +
                "code VARCHAR(20) NOT NULL UNIQUE, " +
                "check_in DATE NOT NULL, " +
                "check_out DATE NOT NULL, " +
                "guests INT NOT NULL, " +
                "nights INT NOT NULL, " +
                "total NUMERIC(10,2) NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "user_id UUID NOT NULL REFERENCES users(id), " +
                "room_number VARCHAR(10) NOT NULL REFERENCES rooms(room_number), " +
                "created_at DATE NOT NULL)";

        String paymentsTable = "CREATE TABLE payments (" +
                "id UUID PRIMARY KEY, " +
                "amount NUMERIC(10,2) NOT NULL, " +
                "method VARCHAR(20) NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "reservation_id UUID NOT NULL REFERENCES reservations(id), " +
                "created_at DATE NOT NULL)";

        String invoicesTable = "CREATE TABLE invoices (" +
                "id UUID PRIMARY KEY, " +
                "invoice_number VARCHAR(30) NOT NULL UNIQUE, " +
                "amount_ht NUMERIC(10,2) NOT NULL, " +
                "tva NUMERIC(10,2) NOT NULL, " +
                "amount_ttc NUMERIC(10,2) NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "payment_id UUID NOT NULL REFERENCES payments(id), " +
                "created_at DATE NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            for (String drop : drops) {
                statement.execute(drop);
            }
            statement.execute(usersTable);
            statement.execute(roomsTable);
            statement.execute(reservationsTable);
            statement.execute(paymentsTable);
            statement.execute(invoicesTable);

            insertUsers(connection);
            insertRooms(connection);

        } catch (SQLException e) {
            throw new BusinessException("Initialisation de la base echouee : " + e.getMessage());
        }
    }

    private static void insertUsers(Connection connection) throws SQLException {
        String sql = "INSERT INTO users (id, full_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, UUID.fromString("11111111-1111-1111-1111-111111111111"));
            ps.setString(2, "Alice Admin");
            ps.setString(3, "alice@hotel.com");
            ps.setString(4, "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9");
            ps.setString(5, "ADMIN");
            ps.executeUpdate();

            ps.setObject(1, UUID.fromString("22222222-2222-2222-2222-222222222222"));
            ps.setString(2, "Bob Client");
            ps.setString(3, "bob@hotel.com");
            ps.setString(4, "186474c1f2c2f735a54c2cf82ee8e87f2a5cd30940e280029363fecedfc5328c");
            ps.setString(5, "CLIENT");
            ps.executeUpdate();
        }
    }

    private static void insertRooms(Connection connection) throws SQLException {
        String sql = "INSERT INTO rooms (room_number, type, capacity, price_per_night, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "101");
            ps.setString(2, "SINGLE");
            ps.setInt(3, 2);
            ps.setBigDecimal(4, new BigDecimal("300.00"));
            ps.setString(5, "AVAILABLE");
            ps.executeUpdate();

            ps.setString(1, "102");
            ps.setString(2, "DOUBLE");
            ps.setInt(3, 4);
            ps.setBigDecimal(4, new BigDecimal("500.00"));
            ps.setString(5, "AVAILABLE");
            ps.executeUpdate();

            ps.setString(1, "201");
            ps.setString(2, "DOUBLE");
            ps.setInt(3, 4);
            ps.setBigDecimal(4, new BigDecimal("550.00"));
            ps.setString(5, "AVAILABLE");
            ps.executeUpdate();

            ps.setString(1, "205");
            ps.setString(2, "SUITE");
            ps.setInt(3, 4);
            ps.setBigDecimal(4, new BigDecimal("900.00"));
            ps.setString(5, "AVAILABLE");
            ps.executeUpdate();

            ps.setString(1, "301");
            ps.setString(2, "SUITE");
            ps.setInt(3, 4);
            ps.setBigDecimal(4, new BigDecimal("1200.00"));
            ps.setString(5, "MAINTENANCE");
            ps.executeUpdate();
        }
    }
}