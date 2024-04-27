package com.backend.btest.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDTO {
    private Long id;
    private UserDTO user;
    private DeviceDTO device;
    private LocalDateTime bookedAt;
    private LocalDateTime returnedAt;
}
