package com.pratik.project.airBnbApp.controller;

import com.pratik.project.airBnbApp.dto.BookingRequest;
import com.pratik.project.airBnbApp.dto.HotelBookingDto;
import com.pratik.project.airBnbApp.service.HotelBooking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class HotelBookingController {

    private final HotelBooking hotelBooking;

    public ResponseEntity<HotelBookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(hotelBooking.initialiseBooking(bookingRequest));
    }

}
