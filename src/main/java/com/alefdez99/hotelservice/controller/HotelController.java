package com.alefdez99.hotelservice.controller;

import com.alefdez99.hotelservice.dto.*;
import com.alefdez99.hotelservice.model.Address;
import com.alefdez99.hotelservice.model.Hotel;
import com.alefdez99.hotelservice.service.HotelMapper;
import com.alefdez99.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public Object getAll(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort
    ) {

        // --- 1) Búsqueda por ciudad (sin paginación) ---
        if (city != null && !city.isBlank()) {
            List<Hotel> hotels = hotelService.findByCity(city);
            return hotels.stream()
                    .map(HotelMapper::toDto)
                    .toList();
        }

        // --- 2) Si NO hay parámetros de paginación → LISTA normal ---
        if (page == null || size == null) {
            return hotelService.findAll()
                    .stream()
                    .map(HotelMapper::toDto)
                    .toList();
        }

        // --- 3) Si hay page + size → PAGINACIÓN ---
        return hotelService.findAllPaged(page, size, sort)
                .map(HotelMapper::toDto);
    }

    @GetMapping("/{id}")
    public HotelResponseDto getById(@PathVariable Long id) {
        return HotelMapper.toDto(hotelService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HotelResponseDto> create(@Validated @RequestBody HotelCreateDto dto) {
        Hotel saved = hotelService.save(HotelMapper.toEntity(dto));
        HotelResponseDto response = HotelMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/hotels/" + saved.getId())).body(response);
    }

    @PutMapping("/{id}/address")
    public HotelResponseDto updateAddress(@PathVariable Long id,
                                          @Validated @RequestBody AddressUpdateDto addressDto) {
        Address newAddress = Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .postalCode(addressDto.getPostalCode())
                .build();
        Hotel updated = hotelService.updateAddress(id, newAddress);
        return HotelMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
