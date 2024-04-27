package com.backend.btest.service;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.dto.BookingRequest;
import com.backend.btest.entity.Booking;
import com.backend.btest.exception.BookingNotFoundException;
import com.backend.btest.exception.DeviceNotAvailableException;
import com.backend.btest.mapper.BookingMapper;
import com.backend.btest.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.backend.btest.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;
    @Mock
    private DeviceService deviceService;
    @Mock
    private UserService userService;
    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllBookingsByUserId_Success() {
        Booking dummyBooking = new Booking();
        when(bookingRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(dummyBooking));

        List<BookingDTO> bookings = bookingService.getAllBookingsByUserId(1L);

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    public void bookDevice_Success() {
        Booking dummyBooking = createBooking();
        when(bookingRepository.save(any(Booking.class))).thenReturn(dummyBooking);

        when(deviceService.fetchDevice(any(Long.class))).thenReturn(createDevice());
        when(userService.fetchUser(any(Long.class))).thenReturn(createUser());

        BookingDTO booking = bookingService.bookDevice(new BookingRequest(DEVICE_ID_1, TESTER_ID_1));

        assertNotNull(booking);
    }

    @Test
    public void returnDevice_Success() {
        Booking dummyBooking = createBooking();

        when(bookingRepository.save(any(Booking.class))).thenReturn(dummyBooking);
        when(deviceService.fetchDevice(any(Long.class))).thenReturn(createDevice());
        when(userService.fetchUser(any(Long.class))).thenReturn(createUser());
        when(userService.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        when(deviceService.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        when(bookingRepository.findByDeviceIdAndUserIdAndReturnedAtIsNull(any(Long.class), any(Long.class))).thenReturn(Optional.of(dummyBooking));


        BookingDTO booking = bookingService.returnDevice(new BookingRequest(DEVICE_ID_1, TESTER_ID_1));

        assertNotNull(booking);
    }

    @Test
    public void returnDevice_AlreadyReturned() {
        Booking dummyBooking = createBooking();
        dummyBooking.setReturnedAt(LocalDateTime.now());

        when(bookingRepository.save(any(Booking.class))).thenReturn(dummyBooking);
        when(deviceService.fetchDevice(any(Long.class))).thenReturn(createDevice());
        when(userService.fetchUser(any(Long.class))).thenReturn(createUser());
        when(userService.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        when(deviceService.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        when(bookingRepository.findByDeviceIdAndUserIdAndReturnedAtIsNull(any(Long.class), any(Long.class))).thenReturn(Optional.of(dummyBooking));

        assertThrows(DeviceNotAvailableException.class, () -> bookingService.returnDevice(new BookingRequest(DEVICE_ID_1, TESTER_ID_1)));

    }

    @Test
    public void getBookingById_BookingExists_ReturnBookingDTO() {
        long bookingId = 1L;
        Booking booking = new Booking();
        BookingDTO expectedBookingDTO = BookingMapper.INSTANCE.toDto(booking);

        when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        BookingDTO actualBookingDTO = bookingService.getBookingById(bookingId);

        assertEquals(expectedBookingDTO, actualBookingDTO);
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    public void getBookingById_BookingDoesNotExist_ThrowException() {
        long bookingId = 1L;

        when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Throwable ex = assertThrows(BookingNotFoundException.class,
                () -> bookingService.getBookingById(bookingId));

        assertEquals("Booking not found for id: " + bookingId, ex.getMessage());
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    public void testGetAllBookings() {
        BookingService bookingService = new BookingService(bookingRepository, deviceService, userService);

        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> mockBookings = Arrays.asList(booking1, booking2);

        when(bookingRepository.findAll()).thenReturn(mockBookings);

        List<BookingDTO> actualBookings = bookingService.getAllBookings();

        assertEquals(2, actualBookings.size());
        verify(bookingRepository, Mockito.times(1)).findAll();
    }
}
