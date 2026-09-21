package org.hotelManag2.service;

import org.hotelManag2.exception.AuthenticationException;
import org.hotelManag2.model.User;
import org.hotelManag2.model.enums.UserRole;
import org.hotelManag2.repository.UserRepository;
import org.hotelManag2.util.ValidationUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AuthService {
    private final UserRepository userRepository;
    private User currentUser;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;

    }
    public User register(String fullName,String email,String password){
        if(!ValidationUtils.isValidEmail(email))
            throw new AuthenticationException("email invalid");
        if(!ValidationUtils.isValidPassword(password))
            throw new AuthenticationException("pass doit >= 6");
        if(userRepository.existsByEmail(email))
            throw new AuthenticationException("email deja exist");

        String hashPass = sha256(password);

        User user = new User(UUID.randomUUID(),fullName,email,hashPass, UserRole.CLIENT);
        userRepository.save(user);
        return user;
    }

    public User login(String email,String password){
        User user = userRepository.findByEmail(email);
        if(user == null || !user.getPassword().equals(sha256(password)))
            throw new AuthenticationException("email ou mot de pass incorrect");
        this.currentUser = user;
        return user;
    }

    public void logout(){
        this.currentUser = null;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticationException("SHA-256 indisponible.");
        }
    }

}