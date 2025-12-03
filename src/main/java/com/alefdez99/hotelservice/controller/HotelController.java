package com.alefdez99.hotelservice.controller;

import com.alefdez99.hotelservice.dto.HotelCreateDto;
import com.alefdez99.hotelservice.dto.HotelResponseDto;
import com.alefdez99.hotelservice.model.Hotel;
import com.alefdez99.hotelservice.service.HotelMapper;
import com.alefdez99.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<HotelResponseDto> getAll() {
        return hotelService.findAll().stream()
                .map(HotelMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public HotelResponseDto getById(@PathVariable Long id) {
        Hotel hotel = hotelService.findById(id);
        return HotelMapper.toDto(hotel);
    }

    @PostMapping
    public ResponseEntity<HotelResponseDto> create(@Valid @RequestBody HotelCreateDto dto) {
        Hotel hotel = HotelMapper.toEntity(dto);
        Hotel saved = hotelService.save(hotel);
        return ResponseEntity.ok(HotelMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hotelService.delete(id);
    }
}
