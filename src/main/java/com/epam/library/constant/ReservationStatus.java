package com.epam.library.constant;

public enum ReservationStatus {
    RESERVED,
    READY,
    ISSUED,
    CANCELLED,
    EXPIRED,
    REJECTED;

    public static boolean containsReservationStatus(String status) {
        for (ReservationStatus reservationStatus : ReservationStatus.values()) {
            if (reservationStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}