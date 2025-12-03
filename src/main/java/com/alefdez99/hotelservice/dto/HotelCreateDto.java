package com.alefdez99.hotelservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelCreateDto {

    @NotBlank
    private String name;

    @Min(1)
    @Max(5)
    private Integer stars;

    @Valid
    private AddressDto address;
}
