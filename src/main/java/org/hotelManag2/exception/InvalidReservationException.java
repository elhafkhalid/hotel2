package org.hotelManag2.exception;

public class InvalidReservationException extends RuntimeException {
    public InvalidReservationException(String msg){
        super(msg);
    }
}