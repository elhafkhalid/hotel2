package org.hotelManag2.policy;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendStrategy implements PricingStrategy {
    private static final BigDecimal WEEKEND = new BigDecimal("1.15");

    public BigDecimal apply(BigDecimal price, LocalDate checkIn,LocalDate checkOut){
        DayOfWeek day = checkIn.getDayOfWeek();

        if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
            return price.multiply(WEEKEND);

        return price;
    }

}
