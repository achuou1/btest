package com.backend.btest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private List<BookingDTO> bookings;
}
