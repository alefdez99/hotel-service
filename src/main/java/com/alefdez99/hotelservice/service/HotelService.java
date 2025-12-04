package com.alefdez99.hotelservice.service;
import org.springframework.data.domain.Page;

import com.alefdez99.hotelservice.model.Address;
import com.alefdez99.hotelservice.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll();
    Page<Hotel> findAllPaged(int page, int size, String sortBy);
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    void delete(Long id);
    List<Hotel> findByCity(String city);
    Hotel updateAddress(Long id, Address newAddress);
}
