package com.alefdez99.hotelservice.service;

import com.alefdez99.hotelservice.model.Hotel;
import com.alefdez99.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
