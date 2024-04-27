package com.backend.btest.service;

import com.backend.btest.dto.DeviceDTO;
import com.backend.btest.entity.Device;
import com.backend.btest.exception.DeviceNotFoundException;
import com.backend.btest.mapper.DeviceMapper;
import com.backend.btest.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device fetchDevice(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found"));
    }

    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return DeviceMapper.INSTANCE.toDto(devices);
    }

    public boolean existsById(Long userId) {
        return deviceRepository.existsById(userId);
    }
}
