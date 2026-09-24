package org.hotelManag2.policy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SeasonalStrategy implements PricingStrategy{
    private static final BigDecimal HIGH_SEASON = new BigDecimal("1.30");
    private static final BigDecimal LOW_SEASON = new BigDecimal("0.85");

    public BigDecimal apply(BigDecimal price, LocalDate checkIn,LocalDate checkOut){
        int month = checkIn.getMonthValue();

        if(month == 7 || month == 8){
            return price.multiply(HIGH_SEASON);
        }

        if(month >= 11 || month <= 2){
            return price.multiply(LOW_SEASON);
        }

        return price;
    }
}
