package com.pratik.project.airBnbApp.service;

import com.pratik.project.airBnbApp.dto.BookingRequest;
import com.pratik.project.airBnbApp.dto.HotelBookingDto;

public interface HotelBooking {
    HotelBookingDto initialiseBooking(BookingRequest bookingRequest);
}
