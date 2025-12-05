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

    // Servicio principal
    private final HotelService hotelService;

    /* 
    * GET /api/hotels
    * Endpoint para: 
    * - Obtener todos los hoteles (sin paginación)
    * - Buscar hoteles por ciudad (sin paginación)
    * - Obtener hoteles paginados (con page + size)
    */
    @GetMapping
    public Object getAll(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort
    ) {

        // Búsqueda por ciudad (sin paginación)
        if (city != null && !city.isBlank()) {
            List<Hotel> hotels = hotelService.findByCity(city);
            return hotels.stream()
                    .map(HotelMapper::toDto)
                    .toList();
        }

        // Si no hay parámetros de paginación → Lista normal (sin paginación)
        if (page == null || size == null) {
            return hotelService.findAll()
                    .stream()
                    .map(HotelMapper::toDto)
                    .toList();
        }

        // Si hay page + size → Paginación
        return hotelService.findAllPaged(page, size, sort)
                .map(HotelMapper::toDto);
    }

    /* 
    * GET /api/hotels/{id}
    * Endpoint para: 
    * - Obtener un hotel por su ID
    */
    @GetMapping("/{id}")
    public HotelResponseDto getById(@PathVariable Long id) {
        return HotelMapper.toDto(hotelService.findById(id));
    }

    /* 
    * POST /api/hotels
    * Endpoint para: 
    * - Crear un nuevo hotel a partir de un DTO
    * - Devuelve el hotel creado con su ID
    */
    @PostMapping
    public ResponseEntity<HotelResponseDto> create(@Validated @RequestBody HotelCreateDto dto) {
        Hotel saved = hotelService.save(HotelMapper.toEntity(dto)); // Convertimos el DTO a entidad antes de guardarlo.
        HotelResponseDto response = HotelMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/hotels/" + saved.getId())).body(response);
    }

    /* 
    * PUT /api/hotels/{id}/address
    * Endpoint para: 
    * - Actualizar la dirección de un hotel por su ID
    */
    @PutMapping("/{id}/address")
    public HotelResponseDto updateAddress(@PathVariable Long id,
                                          @Validated @RequestBody AddressDto addressDto) {
        // Construimos una entidad Address a partir del DTO recibido.                                    
        Address newAddress = Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .postalCode(addressDto.getPostalCode())
                .build();        
        Hotel updated = hotelService.updateAddress(id, newAddress); // Se llama al servicio para actualizar
        return HotelMapper.toDto(updated);
    }

    /* 
    * DELETE /api/hotels/{id}
    * Endpoint para: 
    * - Eliminar un hotel por su ID
    * - Seguridad: solo usuarios con rol ADMIN pueden eliminar hoteles
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
