package com.backend.btest.controller;

import com.backend.btest.dto.BookingRequest;
import com.backend.btest.exception.DeviceNotFoundException;
import com.backend.btest.exception.GlobalExceptionHandler;
import com.backend.btest.service.BookingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.backend.btest.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ImportAutoConfiguration(GlobalExceptionHandler.class)
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "classpath:empty/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/user-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    BookingService bookingService;
    private BookingRequest validBookingRequest;
    private BookingRequest invalidBookingRequest;

    @BeforeEach
    void setUp() {
        validBookingRequest = new BookingRequest(DEVICE_ID_2, TESTER_ID_1);
        invalidBookingRequest = new BookingRequest(null, TESTER_ID_1);
    }

    @Test
    void createBooking_SuccessfullyCreatesAndReturnsBooking() throws Exception {
        Long deviceId = 2L;
        Long userId = 2L;
        Long specificationId = 2L;
        BookingRequest bookingRequest = new BookingRequest(deviceId, userId);
        String jsonRequest = objectMapper.writeValueAsString(bookingRequest);
        MvcResult mvcResult = mockMvc.perform(post("/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response);
        int bookingId = root.path("id").asInt();

        mockMvc.perform(get("/bookings/" + bookingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingId))
                .andExpect(jsonPath("$.user.id").value(userId))
                .andExpect(jsonPath("$.device.id").value(deviceId))
                .andExpect(jsonPath("$.bookedAt").isNotEmpty());

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(userId))
                .andExpect(jsonPath("$.device.id").value(deviceId))
                .andExpect(jsonPath("$.device.specification.id").value(specificationId))
                .andExpect(jsonPath("$.bookedAt").isNotEmpty())
                .andExpect(jsonPath("$.returnedAt").isNotEmpty());


        mockMvc.perform(get("/bookings/" + bookingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingId))
                .andExpect(jsonPath("$.user.id").value(userId))
                .andExpect(jsonPath("$.device.id").value(deviceId))
                .andExpect(jsonPath("$.device.specification.id").value(specificationId))
                .andExpect(jsonPath("$.bookedAt").isNotEmpty())
                .andExpect(jsonPath("$.returnedAt").isNotEmpty());
    }


    @Test
    void bookDevice_WithValidRequest_ReturnsOk() throws Exception {
        mockMvc.perform(post("/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookingRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void bookDevice_WithInvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookingRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returnDevice_WithInvalidDevice_ThrowsDeviceNotFoundException() throws Exception {
        BookingRequest request = new BookingRequest(999L, 1L);

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(result -> assertInstanceOf(DeviceNotFoundException.class, result.getResolvedException()));
    }

    @Test
    void givenValidRequest_whenReturnDevice_thenReturnValidResponse() throws Exception {
        BookingRequest request = new BookingRequest(DEVICE_ID_1, TESTER_ID_1);

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void whenReturnDevice_thenReturnEmptyResponse() throws Exception {
        BookingRequest request = new BookingRequest(DEVICE_ID_1, TESTER_ID_1);

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(TESTER_ID_1))
                .andExpect(jsonPath("$.device.id").value(DEVICE_ID_1))
                .andExpect(jsonPath("$.device.specification.id").value(1))
                .andExpect(jsonPath("$.bookedAt").isNotEmpty())
                .andExpect(jsonPath("$.returnedAt").isNotEmpty());
    }

    @Test
    void givenInvalidDevice_whenReturnDevice_thenThrowDeviceNotFoundException() throws Exception {
        BookingRequest request = new BookingRequest(999L, TESTER_ID_1); // assuming 999L doesn't exist

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInvalidBooking_whenReturnDevice_thenThrowBookingNotFoundException() throws Exception {
        BookingRequest request = new BookingRequest(DEVICE_ID_1, TESTER_ID_2);

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void bookDevice_validBookingRequest_createsBooking() throws Exception {
        BookingRequest bookingRequest = new BookingRequest(DEVICE_ID_3, TESTER_ID_2);

        mockMvc.perform(post("/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void bookDevice_invalidBookingRequest_returnsBadRequest() throws Exception {
        BookingRequest bookingRequest = new BookingRequest(null, TESTER_ID_1);

        mockMvc.perform(post("/bookings/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isBadRequest());
    }
}