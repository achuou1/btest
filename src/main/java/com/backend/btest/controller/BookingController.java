package com.backend.btest.controller;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.dto.BookingRequest;
import com.backend.btest.service.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingController implements IBookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<BookingDTO> bookDevice(@Valid @RequestBody BookingRequest bookingRequest) {
        log.debug("Create booking request {}", bookingRequest);
        var bookingDTO = bookingService.bookDevice(bookingRequest);
        log.debug("Created booking {}", bookingDTO);
        return ResponseEntity.ok().body(bookingDTO);
    }

    @PostMapping("/return")
    public ResponseEntity<BookingDTO> returnDevice(@Valid @RequestBody BookingRequest bookingRequest) {
        log.debug("Return booking request for {}", bookingRequest);
        var bookingDTO = bookingService.returnDevice(bookingRequest);
        log.debug("Returned booking {}", bookingDTO);
        return ResponseEntity.ok().body(bookingDTO);
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        log.debug("Get all bookings");
        return bookingService.getAllBookings();

    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) {
        log.debug("Get all bookings by bookingId {}", bookingId);
        BookingDTO bookingDTO = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getAllBookingsByUserId(@PathVariable Long userId) {
        log.debug("Get all bookings for userId {}", userId);
        List<BookingDTO> bookingsByUserId = bookingService.getAllBookingsByUserId(userId);
        return new ResponseEntity<>(bookingsByUserId, HttpStatus.OK);
    }


}