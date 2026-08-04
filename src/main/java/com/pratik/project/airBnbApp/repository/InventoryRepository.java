package com.pratik.project.airBnbApp.repository;

import com.pratik.project.airBnbApp.entity.Inventory;
import com.pratik.project.airBnbApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);
}
