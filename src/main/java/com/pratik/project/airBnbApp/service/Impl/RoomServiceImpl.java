package com.pratik.project.airBnbApp.service.Impl;

import com.pratik.project.airBnbApp.dto.RoomDto;
import com.pratik.project.airBnbApp.entity.Hotel;
import com.pratik.project.airBnbApp.entity.Room;
import com.pratik.project.airBnbApp.exception.ResourceNotFoundException;
import com.pratik.project.airBnbApp.repository.HotelRepository;
import com.pratik.project.airBnbApp.repository.RoomRepository;
import com.pratik.project.airBnbApp.service.InventoryService;
import com.pratik.project.airBnbApp.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId ,RoomDto roomDto) {

        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setActive(true);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if (hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomInHotel(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ hotelId));
        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with Id: "+ roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with Id: "+ roomId));
        inventoryService.deleteInventories(room);

        roomRepository.deleteById(roomId);
    }
}
