package org.hotelManag2.policy;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PricingStrategy {
   BigDecimal apply(BigDecimal price, LocalDate checkIn,LocalDate checkOut);
}