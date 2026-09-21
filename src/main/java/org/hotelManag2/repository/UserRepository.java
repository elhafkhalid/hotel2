package org.hotelManag2.repository;

import org.hotelManag2.model.User;

public interface UserRepository {
   public boolean existsByEmail(String email);
   void save(User user);
   public User findByEmail(String email);
}

