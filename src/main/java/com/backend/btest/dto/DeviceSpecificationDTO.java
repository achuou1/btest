package com.backend.btest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceSpecificationDTO {
    private Long id;
    private String technology;
    private String bands2G;
    private String bands3G;
    private String bands4G;
    private DeviceDTO device;
}