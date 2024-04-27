package com.backend.btest.controller;

import com.backend.btest.dto.DeviceDTO;
import com.backend.btest.dto.DeviceSpecificationDTO;
import com.backend.btest.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeviceService deviceService;


    @Test
    public void getAllDevicesTest() throws Exception {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .id(1L)
                .name("This is a phone")
                .specification(DeviceSpecificationDTO
                        .builder()
                        .id(2L)
                        .bands2G("yes")
                        .build())
                .build();

        List<DeviceDTO> dummyDevices = Collections.singletonList(deviceDTO);
        when(deviceService.getAllDevices()).thenReturn(dummyDevices);
        mvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dummyDevices.get(0).getId()))
                .andReturn();
        verify(deviceService, times(1)).getAllDevices();
    }


}