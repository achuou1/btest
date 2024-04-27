package com.backend.btest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeviceDTO {
    private Long id;
    private String name;
    private DeviceSpecificationDTO specification;
    private List<BookingDTO> bookings;
}