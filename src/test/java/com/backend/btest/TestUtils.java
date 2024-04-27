package com.backend.btest;

import com.backend.btest.dto.BookingDTO;
import com.backend.btest.dto.DeviceDTO;
import com.backend.btest.dto.DeviceSpecificationDTO;
import com.backend.btest.dto.UserDTO;
import com.backend.btest.entity.Booking;
import com.backend.btest.entity.Device;
import com.backend.btest.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TestUtils {
    public static final long BOOKING_ID_1 = 1L;
    public static final long TESTER_ID_1 = 1L;
    public static final String TESTER_NAME_1 = "tester with id 1";
    public static final long DEVICE_ID_1 = 1L;
    public static final String DEVICE_NAME_1 = "Samsung s10";
    public static final long TESTER_ID_2 = 2L;
    public static final String TESTER_NAME_2 = "tester with id 2";
    public static final long DEVICE_ID_2 = 2L;
    public static final String DEVICE_NAME_2 = "Apple iphone 15";

    public static final long DEVICE_ID_3 = 2L;
    public static final String DEVICE_NAME_3 = "Nokia 3310";

    public static BookingDTO createBookingDto(Long userId, String userName, Long deviceId, String deviceName) {

        // Build DeviceSpecificationDTO
        DeviceSpecificationDTO deviceSpecification = DeviceSpecificationDTO.builder()
                .technology("technology")
                .bands2G("2G")
                .bands3G("3G")
                .bands4G("4G")
                .build();

        // Build DeviceDTO, incorporating DeviceSpecificationDTO
        DeviceDTO device = DeviceDTO.builder()
                .id(deviceId)
                .name(deviceName)
                .specification(deviceSpecification)
                .build();

        // Build UserDTO
        UserDTO user = UserDTO.builder()
                .id(userId)
                .name(userName) // You need to provide a name here
                .build();

        // Build and return BookingDTO, incorporating DeviceDTO and UserDTO
        return BookingDTO.builder()
                .device(device)
                .user(user)
                .bookedAt(LocalDateTime.now())
                .build();
    }

    public static Booking createBooking() {
        Booking booking = new Booking();
        booking.setId(BOOKING_ID_1);
        booking.setUser(createUser());
        booking.setDevice(createDevice());
        booking.setBookedAt(LocalDateTime.now());
        return booking;
    }

    public static User createUser() {
        User user = new User();
        user.setId(TESTER_ID_1);
        user.setName(TESTER_NAME_1);
        return user;
    }

    public static Device createDevice() {
        Device device = new Device();
        device.setId(DEVICE_ID_1);
        device.setName(DEVICE_NAME_1);
        return device;
    }
}
