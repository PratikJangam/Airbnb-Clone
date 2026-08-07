package com.pratik.project.airBnbApp.service.Impl;

import com.pratik.project.airBnbApp.dto.BookingRequest;
import com.pratik.project.airBnbApp.dto.HotelBookingDto;
import com.pratik.project.airBnbApp.entity.*;
import com.pratik.project.airBnbApp.entity.enums.BookingStatus;
import com.pratik.project.airBnbApp.exception.ResourceNotFoundException;
import com.pratik.project.airBnbApp.repository.HotelBookingRepository;
import com.pratik.project.airBnbApp.repository.HotelRepository;
import com.pratik.project.airBnbApp.repository.InventoryRepository;
import com.pratik.project.airBnbApp.repository.RoomRepository;
import com.pratik.project.airBnbApp.service.HotelBooking;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelBookingImpl implements HotelBooking {

    private final HotelBookingRepository hotelBookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public HotelBookingDto initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialising Booking for hotel: {}, room: {}, date {}-{}", bookingRequest.getHotelId(), bookingRequest.getRoomId(),
                bookingRequest.getCheckInDate(), bookingRequest.getCheckoutDate());
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() ->
                new ResourceNotFoundException("Hotel not found with Id: " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() ->
                new ResourceNotFoundException("Room not found with Id: "+ bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(), bookingRequest.getCheckInDate(),
                bookingRequest.getCheckoutDate(), bookingRequest.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckoutDate()) + 1;
        if (inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }

        for (Inventory inventory: inventoryList){
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        User user = new User();
        user.setId(1L); // TODO: remove after spring security

        // TODO: calculate dynamic price
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckoutDate())
                .user(user)
                .amount(BigDecimal.TEN)
                .build();

        booking = hotelBookingRepository.save(booking);
        return modelMapper.map(booking, HotelBookingDto.class);
    }

}
