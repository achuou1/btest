package com.backend.btest.mapper;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingDTO toDto(Booking booking);

    Booking toEntity(BookingDTO bookingDto);

    List<BookingDTO> toDto(List<Booking> device);

    List<Booking> toEntity(List<BookingDTO> bookingDto);
}
