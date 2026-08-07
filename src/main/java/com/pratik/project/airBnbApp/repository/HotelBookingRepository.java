package com.pratik.project.airBnbApp.repository;

import com.pratik.project.airBnbApp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRepository extends JpaRepository<Booking, Long> {
}
