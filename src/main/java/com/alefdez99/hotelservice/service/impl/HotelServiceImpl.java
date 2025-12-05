package com.alefdez99.hotelservice.service.impl;

import com.alefdez99.hotelservice.exception.ResourceNotFoundException;
import com.alefdez99.hotelservice.model.Address;
import com.alefdez99.hotelservice.model.Hotel;
import com.alefdez99.hotelservice.repository.HotelRepository;
import com.alefdez99.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    // Repositorio JPA para acceder a los datos de hoteles
    private final HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll(); // Obtiene todos los hoteles de la base de datos (sin paginación)
    }

    @Override
    public Page<Hotel> findAllPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy)); // Configura la paginación y ordenación
        return hotelRepository.findAll(pageable); // Obtiene hoteles paginados y ordenados
    }


    @Override
    public Hotel findById(Long id) {
        // Busca hotel por ID o lanza una excepción si no existe
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID " + id));
    }


    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel); // Guarda un nuevo hotel en la base de datos
    }

    @Override
    public void delete(Long id) {
        // Elimina un hotel por su ID, lanzando excepción si no existe
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public List<Hotel> findByCity(String city) {
        return hotelRepository.findByAddressCityIgnoreCase(city); // Busca hoteles filtrando por ciudad (ignorando mayúsculas/minúsculas)
    }

    @Override
    public Hotel updateAddress(Long id, Address newAddress) {
        Hotel hotel = findById(id);
        hotel.setAddress(newAddress);
        return hotelRepository.save(hotel); // Actualiza la dirección de un hotel existente
    }
}
