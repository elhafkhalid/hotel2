package org.hotelManag2.repository;

import org.hotelManag2.model.User;

import java.util.UUID;

public interface UserRepository {
   public boolean existsByEmail(String email);
   void save(User user);
   public User findByEmail(String email);
   public void updatePassword(UUID id,String newPasswordHash);
}

