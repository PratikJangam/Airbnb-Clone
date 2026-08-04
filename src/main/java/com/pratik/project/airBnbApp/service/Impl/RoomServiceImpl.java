package com.pratik.project.airBnbApp.service.Impl;

import com.pratik.project.airBnbApp.dto.RoomDto;
import com.pratik.project.airBnbApp.entity.Hotel;
import com.pratik.project.airBnbApp.entity.Room;
import com.pratik.project.airBnbApp.exception.ResourceNotFoundException;
import com.pratik.project.airBnbApp.repository.HotelRepository;
import com.pratik.project.airBnbApp.repository.RoomRepository;
import com.pratik.project.airBnbApp.service.RoomService;
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

    @Override
    public RoomDto createNewRoom(Long hotelId ,RoomDto roomDto) {

        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+ hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setActive(false);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        // TODO: create inventory as soon as room is created and hotel is active

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
    public void deleteRoomById(Long roomId) {
        boolean exist = roomRepository.existsById(roomId);
        if (!exist){
            throw new ResourceNotFoundException("Room not found with Id: "+ roomId);
        }
        roomRepository.deleteById(roomId);

        // TODO: delete all future inventories for this room
    }
}
