package com.alefdez99.hotelservice.controller;

import com.alefdez99.hotelservice.dto.AddressDto;
import com.alefdez99.hotelservice.dto.HotelCreateDto;
import com.alefdez99.hotelservice.exception.ResourceNotFoundException;
import com.alefdez99.hotelservice.model.Address;
import com.alefdez99.hotelservice.model.Hotel;
import com.alefdez99.hotelservice.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Import;
import com.alefdez99.hotelservice.config.SecurityConfig;
import com.alefdez99.hotelservice.config.TestMethodSecurityConfig;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HotelController.class)
@Import({SecurityConfig.class, TestMethodSecurityConfig.class})
@AutoConfigureMockMvc(addFilters = true)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    /* Helper method to build a sample Hotel */
    private Hotel sampleHotel() {
        return Hotel.builder()
                .id(1L)
                .name("Hotel Test")
                .stars(4)
                .address(Address.builder()
                        .street("Main St")
                        .city("Madrid")
                        .country("España")
                        .postalCode("28001")
                        .build())
                .build();
    }

    /* GET /api/hotels — list all hotels */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetAllHotels() throws Exception {
        Mockito.when(hotelService.findAll()).thenReturn(List.of(sampleHotel()));

        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel Test"));
    }

    /* GET /api/hotels/{id} — retrieve a single hotel */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetHotelById() throws Exception {
        Mockito.when(hotelService.findById(1L)).thenReturn(sampleHotel());

        mockMvc.perform(get("/api/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel Test"));
    }

    /* Test GET /api/hotels/{id} — hotel not found */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testGetHotelById_NotFound() throws Exception {
        Mockito.when(hotelService.findById(1L)).thenThrow(new ResourceNotFoundException("Hotel not found"));

        mockMvc.perform(get("/api/hotels/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    /* POST /api/hotels — create a new hotel */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testCreateHotel() throws Exception {
        HotelCreateDto dto = new HotelCreateDto(
                "Hotel Test",
                4,
                new com.alefdez99.hotelservice.dto.AddressDto(
                        "Main St", "Madrid", "España", "28001")
        );

        Hotel saved = sampleHotel();
        Mockito.when(hotelService.save(any(Hotel.class))).thenReturn(saved);

        mockMvc.perform(post("/api/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/hotels/1"))
                .andExpect(jsonPath("$.id").value(1));
    }

    /* PUT /api/hotels/{id}/address — update hotel address */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testUpdateAddress() throws Exception {
        AddressDto dto = new AddressDto("New St", "Madrid", "España", "28002");

        Hotel updated = sampleHotel();
        updated.getAddress().setStreet("New St");
        updated.getAddress().setPostalCode("28002");

        Mockito.when(hotelService.updateAddress(any(Long.class), any(Address.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/hotels/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address.street").value("New St"));
    }

    /* DELETE /api/hotels/{id} — restricted to ADMIN */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void testDeleteHotel_UnauthorizedForUser() throws Exception {
        mockMvc.perform(delete("/api/hotels/1"))
                .andExpect(status().isForbidden());
    }

    // Test deletion as ADMIN
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteHotel_AsAdmin() throws Exception {
        Mockito.doNothing().when(hotelService).delete(1L);

        mockMvc.perform(delete("/api/hotels/1"))
                .andExpect(status().isNoContent());
    }
}