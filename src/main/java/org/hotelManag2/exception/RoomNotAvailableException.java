package org.hotelManag2.exception;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String msg){
        super(msg);
    }
}