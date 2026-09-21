package org.hotelManag2.repository.jdbc;

import org.hotelManag2.db.DatabaseConnection;
import org.hotelManag2.exception.BusinessException;
import org.hotelManag2.model.User;
import org.hotelManag2.model.enums.UserRole;
import org.hotelManag2.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepositoryJDBC implements UserRepository {
    public boolean existsByEmail(String email){
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,email);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        } catch(SQLException e){
            throw new BusinessException("email echouee");
        }
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id,full_name,email,password,role FROM users WHERE email = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

        ps.setString(1,email);

        try (ResultSet rs = ps.executeQuery()){
            if(rs.next()) {
                return mapUser(rs);
            }

        }
        return null;

        }catch(SQLException e){
            throw new BusinessException("Erreur lors de la recherche de l'utilisateur par email");
        }

    }

    public void save(User user){
        String sql = "INSERT INTO users (id,full_name,email,password,role) VALUES (?,?,?,?,?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setObject(1,user.getId());
            ps.setString(2,user.getFullName());
            ps.setString(3,user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5,user.getRole().name());

            ps.executeUpdate();

        }catch(SQLException e){
            throw new BusinessException("Enregistrement de l'utilisateur échoué : " + e.getMessage());
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getObject("id", UUID.class),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password"),
                UserRole.valueOf(rs.getString("role"))
        );
    }
}
