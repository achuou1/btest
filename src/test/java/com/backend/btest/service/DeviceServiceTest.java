package com.backend.btest.service;

import com.backend.btest.dto.DeviceDTO;
import com.backend.btest.entity.Device;
import com.backend.btest.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class DeviceServiceTest {
    @InjectMocks
    private DeviceService deviceService;

    @Mock
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllDevices_Success() {
        Device dummyDevice1 = new Device();
        Device dummyDevice2 = new Device();
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(dummyDevice1, dummyDevice2));

        List<DeviceDTO> devices = deviceService.getAllDevices();

        assertNotNull(devices);
        assertEquals(2, devices.size());
    }


}