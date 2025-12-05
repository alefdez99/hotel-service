package com.alefdez99.hotelservice.service;
import org.springframework.data.domain.Page;

import com.alefdez99.hotelservice.model.Address;
import com.alefdez99.hotelservice.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll(); // Obtiene todos los hoteles
    Page<Hotel> findAllPaged(int page, int size, String sortBy); // Obtiene hoteles paginados y ordenados
    Hotel findById(Long id); // Busca un hotel por su ID
    Hotel save(Hotel hotel); // Guarda un nuevo hotel
    void delete(Long id); // Elimina un hotel por su ID
    List<Hotel> findByCity(String city); // Busca hoteles filtrando por ciudad
    Hotel updateAddress(Long id, Address newAddress); // Actualiza la dirección de un hotel existente
}
