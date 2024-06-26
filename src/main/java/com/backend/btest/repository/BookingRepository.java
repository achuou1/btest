package com.backend.btest.repository;

import com.backend.btest.entity.Booking;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByDeviceIdAndReturnedAtIsNull(Long deviceId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Booking> findByDeviceIdAndUserIdAndReturnedAtIsNull(Long deviceId, Long userId);

    List<Booking> findByUserId(Long id);

}
