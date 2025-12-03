package com.alefdez99.hotelservice.service;

import com.alefdez99.hotelservice.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll();
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    void delete(Long id);
}
