package org.hotelManag2.policy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DurationStrategy implements PricingStrategy{
    private static final BigDecimal LONG_STAY = new BigDecimal("0.90");
    private static final BigDecimal VERY_LONG_STAY = new BigDecimal("0.85");

    public BigDecimal apply(BigDecimal price, LocalDate checkIn,LocalDate checkOut){

        long nights = ChronoUnit.DAYS.between(checkIn,checkOut);
        if(nights >= 14) return  price.multiply(VERY_LONG_STAY);
        else if(nights >= 7) return price.multiply(LONG_STAY);

        return price;
    }
}
