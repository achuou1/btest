package com.backend.btest.controller;

import com.backend.btest.dto.DeviceDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDeviceController {

    @ApiOperation(value = "Get all the devices")
    ResponseEntity<List<DeviceDTO>> getAllDevices();

}