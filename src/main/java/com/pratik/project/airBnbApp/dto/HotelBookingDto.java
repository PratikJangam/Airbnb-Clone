package com.pratik.project.airBnbApp.dto;

import com.pratik.project.airBnbApp.entity.*;
import com.pratik.project.airBnbApp.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class HotelBookingDto {

    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private Integer roomCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
