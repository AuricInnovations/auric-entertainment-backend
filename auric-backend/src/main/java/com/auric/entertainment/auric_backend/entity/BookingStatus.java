package com.auric.entertainment.auric_backend.entity;

public enum BookingStatus {
    PENDING_PAYMENT,  // user created booking, not paid yet
    CONFIRMED,        // admin confirmed after payment
    CANCELLED,        // admin/user cancelled
    EXPIRED           // auto-expired hold
}