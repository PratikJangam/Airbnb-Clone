package com.pratik.project.airBnbApp.service;

import com.pratik.project.airBnbApp.dto.HotelDto;
import com.pratik.project.airBnbApp.dto.HotelInfoDto;
import com.pratik.project.airBnbApp.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long id);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
