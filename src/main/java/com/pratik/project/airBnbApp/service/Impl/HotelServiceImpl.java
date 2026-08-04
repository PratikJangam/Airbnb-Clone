package com.pratik.project.airBnbApp.service.Impl;

import com.pratik.project.airBnbApp.dto.HotelDto;
import com.pratik.project.airBnbApp.dto.RoomDto;
import com.pratik.project.airBnbApp.entity.Hotel;
import com.pratik.project.airBnbApp.entity.Room;
import com.pratik.project.airBnbApp.exception.ResourceNotFoundException;
import com.pratik.project.airBnbApp.repository.HotelRepository;
import com.pratik.project.airBnbApp.repository.RoomRepository;
import com.pratik.project.airBnbApp.service.HotelService;
import com.pratik.project.airBnbApp.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel with name {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created new hotel with id {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Get hotel with id {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ id));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ id));
        for (Room room: hotel.getRooms()){
            inventoryService.deleteInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ id));
        hotel.setActive(true);
        // Assuming only do it once
        for (Room room: hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }

        hotelRepository.save(hotel);
    }
}
