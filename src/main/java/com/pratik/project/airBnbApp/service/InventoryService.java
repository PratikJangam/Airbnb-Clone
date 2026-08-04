package com.pratik.project.airBnbApp.service;

import com.pratik.project.airBnbApp.dto.HotelDto;
import com.pratik.project.airBnbApp.dto.HotelSearchRequest;
import com.pratik.project.airBnbApp.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
