package com.backend.btest.controller;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.dto.BookingRequest;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBookingController {

    @ApiOperation(value = "Creates a new booking")
    ResponseEntity<BookingDTO> bookDevice(@Valid @RequestBody BookingRequest bookingRequest);

    @ApiOperation(value = "Returns the device")
    ResponseEntity<BookingDTO> returnDevice(@Valid @RequestBody BookingRequest bookingRequest);

    @ApiOperation(value = "Get all the bookings")
    List<BookingDTO> getAllBookings();

    @ApiOperation(value = "Get the booking by ID")
    ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId);

    @ApiOperation(value = "Get all the bookings by User ID")
    ResponseEntity<List<BookingDTO>> getAllBookingsByUserId(@PathVariable Long userId);

}
