package org.hotelManag2.policy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingWindowStrategy implements PricingStrategy{

    private static final BigDecimal EARLY_BOOKING = new BigDecimal("0.95");
    private static final BigDecimal LAST_MINUTE = new BigDecimal("1.10");

    public BigDecimal apply(BigDecimal price, LocalDate checkIn,LocalDate checkOut){
        long daysBeforCheckIn = ChronoUnit.DAYS.between(LocalDate.now(),checkIn);

        if(daysBeforCheckIn >= 30) return price.multiply(EARLY_BOOKING);
        if(daysBeforCheckIn <= 3) return price.multiply(LAST_MINUTE);

        return price;
    }
}
