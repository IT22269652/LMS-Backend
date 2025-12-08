package net.javaguides.lms.repository;

import net.javaguides.lms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(Integer userId);
    List<Reservation> findByBookId(Integer bookId);
    List<Reservation> findByStatus(Reservation.ReservationStatus status);
}