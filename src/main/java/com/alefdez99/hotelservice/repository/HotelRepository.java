package com.alefdez99.hotelservice.repository;

import com.alefdez99.hotelservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* Repositorio JPA para Hotel con consultas personalizadas */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Busca hoteles filtrando por ciudad (ignorando mayúsculas/minúsculas)
    List<Hotel> findByAddressCityIgnoreCase(String city);
}
