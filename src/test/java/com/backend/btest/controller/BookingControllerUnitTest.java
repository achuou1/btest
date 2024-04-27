package com.backend.btest.controller;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.dto.BookingRequest;
import com.backend.btest.dto.UserDTO;
import com.backend.btest.exception.BadRequestException;
import com.backend.btest.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.backend.btest.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class BookingControllerUnitTest {


    private BookingController bookingController;

    private final BookingService bookingService = mock(BookingService.class);
    private BookingRequest validBookingRequest;
    private BookingRequest invalidBookingRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingController = new BookingController(bookingService);
        validBookingRequest = new BookingRequest(DEVICE_ID_2, TESTER_ID_1);
        invalidBookingRequest = new BookingRequest(null, TESTER_ID_1);
    }

    @Test
    void createBooking_successfullyCreatesBooking() {
        BookingRequest bookingRequest = BookingRequest.builder().deviceId(DEVICE_ID_1).userId(TESTER_ID_1).build();
        BookingDTO expectedBookingDto = createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1);
        when(bookingService.bookDevice(any(BookingRequest.class))).thenReturn(expectedBookingDto);

        BookingDTO bookingResult = bookingController.bookDevice(bookingRequest).getBody();
        assertEquals(expectedBookingDto, bookingResult);

        verify(bookingService).bookDevice(any(BookingRequest.class));
    }

    @Test
    public void createBooking_whenNoResponseFromService_thenReturnsEmptyResponse() {
        
        BookingRequest bookingRequest = BookingRequest.builder().deviceId(3L).userId(3L).build();

        when(bookingService.bookDevice(bookingRequest)).thenReturn(null);

        
        ResponseEntity<BookingDTO> responseEntity = bookingController.bookDevice(bookingRequest);

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void createBooking_validatesNonNullResponse() {
        // Given
        BookingRequest bookingRequest = BookingRequest.builder().deviceId(DEVICE_ID_1).userId(TESTER_ID_1).build();
        BookingDTO expectedBookingDto = createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1);
        when(bookingService.bookDevice(any(BookingRequest.class))).thenReturn(expectedBookingDto);

        // When
        BookingDTO bookingResult = bookingController.bookDevice(bookingRequest).getBody();

        // Then
        assertNotEquals(null, bookingResult);
        verify(bookingService).bookDevice(any(BookingRequest.class));
    }

    @Test
    public void returnDevice_whenNoResponseFromService_thenReturnsEmptyResponse() {
        
        BookingRequest bookingRequest = BookingRequest.builder().deviceId(3L).userId(3L).build();

        when(bookingService.returnDevice(bookingRequest)).thenReturn(null);

        
        ResponseEntity<BookingDTO> responseEntity = bookingController.returnDevice(bookingRequest);

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void createBooking_validatesNonEmptyUser() {
        // Given
        BookingRequest bookingRequest = BookingRequest.builder().deviceId(1L).userId(1L).build();
        BookingDTO expectedBookingDto = createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1);
        when(bookingService.bookDevice(any(BookingRequest.class))).thenReturn(expectedBookingDto);

        // When
        BookingDTO bookingResult = bookingController.bookDevice(bookingRequest).getBody();

        // Then
        assertNotEquals(null, bookingResult.getUser());
        validateNonEmptyUser(bookingResult.getUser());
        verify(bookingService).bookDevice(any(BookingRequest.class));
    }

    @Test
    public void returnDevice_successfullyReturnsDevice() {
        
        BookingRequest bookingRequest = BookingRequest.builder()
                .deviceId(2L)
                .userId(2L)
                .build();
        BookingDTO returnedBookingDTO = createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1);
        returnedBookingDTO.setReturnedAt(LocalDateTime.now());

        when(bookingService.returnDevice(bookingRequest)).thenReturn(returnedBookingDTO);

        
        ResponseEntity<BookingDTO> responseEntity = bookingController.returnDevice(bookingRequest);

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(returnedBookingDTO, responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getReturnedAt());
        verify(bookingService).returnDevice(bookingRequest);
    }

    @Test
    public void returnDevice_whenParametersAreNullInRequest_thenThrowsBadRequestException() {
        
        BookingRequest bookingRequest = new BookingRequest();

        when(bookingService.returnDevice(any())).thenThrow(BadRequestException.class);


        Exception exception = assertThrows(BadRequestException.class, () -> {
            bookingController.returnDevice(bookingRequest);
        });

        verify(bookingService, times(0)).returnDevice(null);
        assertNotNull(exception);
    }

    @Test
    public void getAllBookings_ReturnsBookings() {
        
        when(bookingService.getAllBookings())
                .thenReturn(
                        Arrays.asList(
                                createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1),
                                createBookingDto(TESTER_ID_2, TESTER_NAME_2, DEVICE_ID_2, DEVICE_NAME_2)
                        )
                );

        
        List<BookingDTO> bookings = bookingController.getAllBookings();

        
        assertThat(bookings, hasSize(2));
    }

    @Test
    public void getAllBookingsByUserId_ReturnsBookings() {
        
        Long userId = 1L;
        when(bookingService.getAllBookingsByUserId(userId)).thenReturn(
                Arrays.asList(
                        createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1),
                        createBookingDto(TESTER_ID_2, TESTER_NAME_2, DEVICE_ID_2, DEVICE_NAME_2)
                )
        );

        
        List<BookingDTO> bookings = bookingController.getAllBookingsByUserId(userId).getBody();

        
        assertThat(bookings, hasSize(2));
    }

    @Test
    public void getAllBookingsByUserId_invalidUserId_ReturnsEmptyList() {
        
        Long userId = 999L;
        when(bookingService.getAllBookingsByUserId(userId)).thenReturn(Collections.emptyList());

        
        List<BookingDTO> bookings = bookingController.getAllBookingsByUserId(userId).getBody();

        
        assertThat(bookings, is(empty()));
    }

    @Test
    public void getBookingById_ValidId_ReturnsBooking() {
        
        Long bookingId = 1L;
        BookingDTO bookingDTO = createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1);
        when(bookingService.getBookingById(bookingId)).thenReturn(bookingDTO);

        
        BookingDTO retrievedBooking = bookingController.getBookingById(bookingId).getBody();


        assertEquals(bookingDTO, retrievedBooking);
    }

    @Test
    void returnDevice_NoResponseFromService_ReturnsEmptyResponse() {
        when(bookingService.returnDevice(invalidBookingRequest)).thenReturn(null);

        assertNull(bookingController.returnDevice(invalidBookingRequest).getBody());
    }

    @Test
    void returnDevice_ValidRequest_ReturnsDevice() {
        BookingDTO returnedBookingDTO = createBookingDto(TESTER_ID_1, TESTER_NAME_1, DEVICE_ID_1, DEVICE_NAME_1);
        returnedBookingDTO.setReturnedAt(LocalDateTime.now());

        when(bookingService.returnDevice(validBookingRequest)).thenReturn(returnedBookingDTO);
        BookingDTO responseBookingDTO = bookingController.returnDevice(validBookingRequest).getBody();

        assertEquals(returnedBookingDTO, responseBookingDTO);
        assertNotNull(responseBookingDTO.getReturnedAt());
        verify(bookingService).returnDevice(validBookingRequest);
    }

    void validateNonEmptyUser(UserDTO user) {
        assertNotEquals(null, user.getId());
        assertNotEquals(null, user.getName());
    }


}