package com.alefdez99.hotelservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponseDto {

    private Long id;
    private String name;
    private Integer stars;
    private AddressDto address;
}
