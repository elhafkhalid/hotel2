package org.hotelManag2.model;

import org.hotelManag2.model.enums.PaymentMethod;
import org.hotelManag2.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Payment {
    private UUID id;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private UUID reservationId;
    private LocalDate createdAt;

    public Payment() {
    }

    public Payment(UUID id, BigDecimal amount, PaymentMethod method, PaymentStatus status,
                   UUID reservationId, LocalDate createdAt) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.reservationId = reservationId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}