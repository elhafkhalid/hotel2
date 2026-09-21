package org.hotelManag2.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private ValidationUtils(){

    }

    public static boolean isValidEmail(String email){
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPassword(String pass){
        return pass != null && pass.length() >= 6;
    }

    public static boolean isValidPeriod(LocalDate checkIn,LocalDate checkOut){
        return checkIn != null && checkOut != null
                && checkIn.isBefore(checkOut)
                && !checkIn.isAfter(LocalDate.now());
    }

}