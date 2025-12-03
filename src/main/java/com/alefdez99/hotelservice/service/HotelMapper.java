package com.alefdez99.hotelservice.service;

import com.alefdez99.hotelservice.dto.*;
import com.alefdez99.hotelservice.model.*;

public class HotelMapper {

    public static Hotel toEntity(HotelCreateDto dto) {
        return Hotel.builder()
                .name(dto.getName())
                .stars(dto.getStars())
                .address(Address.builder()
                        .street(dto.getAddress().getStreet())
                        .city(dto.getAddress().getCity())
                        .country(dto.getAddress().getCountry())
                        .postalCode(dto.getAddress().getPostalCode())
                        .build())
                .build();
    }

    public static HotelResponseDto toDto(Hotel hotel) {
        return HotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .stars(hotel.getStars())
                .address(AddressDto.builder()
                        .street(hotel.getAddress().getStreet())
                        .city(hotel.getAddress().getCity())
                        .country(hotel.getAddress().getCountry())
                        .postalCode(hotel.getAddress().getPostalCode())
                        .build())
                .build();
    }
}
