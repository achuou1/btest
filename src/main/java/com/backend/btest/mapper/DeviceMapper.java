package com.backend.btest.mapper;

import com.backend.btest.dto.DeviceDTO;
import com.backend.btest.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceDTO toDto(Device device);

    Device toEntity(DeviceDTO deviceDto);

    List<DeviceDTO> toDto(List<Device> device);

    List<Device> toEntity(List<DeviceDTO> deviceDto);
}
