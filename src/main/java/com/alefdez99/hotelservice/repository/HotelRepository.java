package com.alefdez99.hotelservice.repository;

import com.alefdez99.hotelservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByAddressCityIgnoreCase(String city);
}
