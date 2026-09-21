package org.hotelManag2.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateUtils(){

    }

    public static LocalDate parse(String value) {
        return LocalDate.parse(value,FORMATTER);
    }

    public static String format(LocalDate date){

        return date.format(FORMATTER);
    }

    public static int nights(LocalDate checkIn,LocalDate checkOut){
        return (int) ChronoUnit.DAYS.between(checkIn,checkOut);
    }

    public static boolean isNotOverLaps(LocalDate startA,LocalDate endA,LocalDate startB,LocalDate endB){
        return endB.isBefore(startA) || startB.isAfter(endA);
    }
}
