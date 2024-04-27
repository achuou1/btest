package com.backend.btest.service;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.dto.BookingRequest;
import com.backend.btest.entity.Booking;
import com.backend.btest.entity.Device;
import com.backend.btest.entity.User;
import com.backend.btest.exception.*;
import com.backend.btest.mapper.BookingMapper;
import com.backend.btest.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DeviceService deviceService;
    private final UserService userService;

    public BookingService(
            BookingRepository bookingRepository,
            DeviceService deviceService,
            UserService userService) {
        this.bookingRepository = bookingRepository;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    public BookingDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found for id: " + bookingId));
        return BookingMapper.INSTANCE.toDto(booking);
    }

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return BookingMapper.INSTANCE.toDto(bookings);
    }

    public List<BookingDTO> getAllBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return BookingMapper.INSTANCE.toDto(bookings);
    }

    public BookingDTO bookDevice(BookingRequest bookingRequest) {
        log.debug("Booking request {}", bookingRequest);

        validateInput(bookingRequest);
        Long deviceId = bookingRequest.getDeviceId();
        Long userId = bookingRequest.getUserId();
        Device device = deviceService.fetchDevice(deviceId);
        User user = userService.fetchUser(userId);

        verifyDeviceIsAvailable(device);

        Booking booking = createBooking(user, device);
        BookingDTO bookingDTO = saveBooking(booking);

        log.debug("Created booking {}", bookingDTO);

        return bookingDTO;
    }

    public BookingDTO returnDevice(BookingRequest bookingRequest) {
        log.debug("Return booking request for {}", bookingRequest);

        validateInput(bookingRequest);
        Long deviceId = bookingRequest.getDeviceId();
        Long userId = bookingRequest.getUserId();
        validateUserAndDeviceIds(userId, deviceId);

        Booking booking = findCurrentBookingForDeviceAndUser(deviceId, userId);
        BookingDTO bookingDTO = returnBooking(booking);

        log.debug("Returned booking {}", bookingDTO);
        return bookingDTO;
    }


    private Booking findCurrentBookingForDeviceAndUser(Long deviceId, Long userId) {
        return bookingRepository.findByDeviceIdAndUserIdAndReturnedAtIsNull(deviceId, userId)
                .orElseThrow(() -> new DeviceNotAvailableException("No active booking found for this device and user."));
    }

    private BookingDTO returnBooking(Booking booking) {
        if (booking.getReturnedAt() != null) {
            throw new DeviceNotAvailableException("This booking has already been returned.");
        }
        booking.setReturnedAt(LocalDateTime.now());
        return saveBooking(booking);
    }

    private void verifyDeviceIsAvailable(Device device) {
        if (isBooked(device.getId())) {
            throw new DeviceNotAvailableException("The device is currently unavailable as it is already booked.");
        }
    }

    private boolean isBooked(Long deviceId) {
        return bookingRepository.existsByDeviceIdAndReturnedAtIsNull(deviceId);
    }

    private Booking createBooking(User user, Device device) {
        Booking booking = new Booking();
        booking.setDevice(device);
        booking.setUser(user);
        booking.setBookedAt(LocalDateTime.now());
        return booking;
    }

    private BookingDTO saveBooking(Booking booking) {
        log.debug("Saving booking {}", booking);
        booking = bookingRepository.save(booking);
        return BookingMapper.INSTANCE.toDto(booking);

    }

    private void validateInput(BookingRequest bookingRequest) {
        Long deviceId = bookingRequest.getDeviceId();
        Long userId = bookingRequest.getUserId();

        if (deviceId == null || deviceId <= 0) {
            throw new BadRequestException("Device ID cannot be null and must be a positive number.");
        }

        if (userId == null || userId <= 0) {
            throw new BadRequestException("User ID cannot be null and must be a positive number.");
        }
    }

    private void validateUserAndDeviceIds(Long userId, Long deviceId) {
        // Check if the user exists in the database:
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException("User not found"); // Or any other exception handling as needed
        }
        if (!deviceService.existsById(deviceId)) {
            throw new DeviceNotFoundException("Device not found"); // Or any other exception handling as needed
        }
    }
}