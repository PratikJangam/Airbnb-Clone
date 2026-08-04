package com.pratik.project.airBnbApp.service;

import com.pratik.project.airBnbApp.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteInventories(Room room);
}
