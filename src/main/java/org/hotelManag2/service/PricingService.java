package org.hotelManag2.service;

import org.hotelManag2.policy.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PricingService {

    private final List<PricingStrategy> strategies = List.of(
            new SeasonalStrategy(),
            new WeekendStrategy(),
            new DurationStrategy(),
            new BookingWindowStrategy()
    );

    public BigDecimal calculatePricePerNight(BigDecimal price, LocalDate checkIn,LocalDate checkOut){
        return applyStrategies(price,checkIn,checkOut).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotal(BigDecimal price,LocalDate checkIn,LocalDate checkOut){

        long night = ChronoUnit.DAYS.between(checkIn,checkOut);
        return applyStrategies(price,checkIn,checkOut)
                .multiply(BigDecimal.valueOf(night))
                .setScale(2,RoundingMode.HALF_UP);

    }

    private BigDecimal applyStrategies(BigDecimal price,LocalDate checkIn,LocalDate checkOut){
        for(PricingStrategy strategy : strategies){
            price = strategy.apply(price,checkIn,checkOut);
        }

        return price;
    }

}