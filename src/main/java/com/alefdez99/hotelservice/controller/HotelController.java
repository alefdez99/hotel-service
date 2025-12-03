package com.alefdez99.hotelservice.controller;

import com.alefdez99.hotelservice.model.Hotel;
import com.alefdez99.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<Hotel> getHotels() {
        return hotelService.findAll();
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.save(hotel);
    }
}
