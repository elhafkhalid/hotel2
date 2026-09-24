package org.hotelManag2.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PricingService {
    private static final BigDecimal HIGH_SEASON = new BigDecimal("1.30");
    private static final BigDecimal LOW_SEASON = new BigDecimal("0.85");
    private static final BigDecimal WEEKEND = new BigDecimal("1.15");
    private static final BigDecimal LONG_STAY = new BigDecimal("0.90");
    private static final BigDecimal VERY_LONG_STAY = new BigDecimal("0.85");
    private static final BigDecimal EARLY_BOOKING = new BigDecimal("0.95");
    private static final BigDecimal LAST_MINUTE = new BigDecimal("1.10");

    public BigDecimal calculatePricePerNight(BigDecimal basePrice, LocalDate checkIn,LocalDate checkOut){
        return basePrice.multiply(calculateCoefficient(checkIn,checkOut)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotal(BigDecimal basePrice,LocalDate checkIn,LocalDate checkOut){
        long nights = ChronoUnit.DAYS.between(checkIn,checkOut);

        return basePrice.multiply(calculateCoefficient(checkIn,checkOut))
                .multiply(BigDecimal.valueOf(nights))
                .setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCoefficient(LocalDate checkIn,LocalDate checkOut){
        BigDecimal coefficient = BigDecimal.ONE;

        if(isHighSeason(checkIn)){
            coefficient = coefficient.multiply(HIGH_SEASON);
        }

        if(isLowSeason(checkIn)) {
            coefficient = coefficient.multiply(LOW_SEASON);
        }

        if(isWeekend(checkIn)){
            coefficient = coefficient.multiply(WEEKEND);
        }

        long nights = ChronoUnit.DAYS.between(checkIn,checkOut);
        if(nights >= 14) coefficient = coefficient.multiply(VERY_LONG_STAY);
        else if(nights >= 7) coefficient = coefficient.multiply(LONG_STAY);

        long daysBeforCheckIn = ChronoUnit.DAYS.between(LocalDate.now(),checkIn);
        if(daysBeforCheckIn >= 30) coefficient = coefficient.multiply(EARLY_BOOKING);
        else if(daysBeforCheckIn <= 3) coefficient = coefficient.multiply(LAST_MINUTE);

        return coefficient;

    }

    public boolean isHighSeason(LocalDate date){
        return date.getMonthValue() == 7
                ||
                date.getMonthValue() == 8;
    }

    public boolean isLowSeason(LocalDate date){
        int month = date.getMonthValue();
        return month == 11 || month == 12 || month == 1 || month == 2;
    }

    public boolean isWeekend(LocalDate date){
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}