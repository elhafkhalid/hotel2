package org.hotelManag2.model;

import org.hotelManag2.model.enums.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Invoice {
    private UUID id;
    private String invoiceNumber;
    private BigDecimal amountHt;
    private BigDecimal tva;
    private BigDecimal amountTtc;
    private InvoiceStatus status;
    private UUID paymentId;
    private LocalDate createdAt;

    public Invoice() {
    }

    public Invoice(UUID id, String invoiceNumber, BigDecimal amountHt, BigDecimal tva,
                   BigDecimal amountTtc, InvoiceStatus status, UUID paymentId, LocalDate createdAt) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.amountHt = amountHt;
        this.tva = tva;
        this.amountTtc = amountTtc;
        this.status = status;
        this.paymentId = paymentId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getAmountHt() {
        return amountHt;
    }

    public void setAmountHt(BigDecimal amountHt) {
        this.amountHt = amountHt;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getAmountTtc() {
        return amountTtc;
    }

    public void setAmountTtc(BigDecimal amountTtc) {
        this.amountTtc = amountTtc;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}