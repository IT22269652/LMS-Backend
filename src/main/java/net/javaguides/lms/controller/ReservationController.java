package net.javaguides.lms.controller;

import jakarta.validation.Valid;
import net.javaguides.lms.dto.ReservationRequest;
import net.javaguides.lms.entity.Reservation;
import net.javaguides.lms.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> createReservation(
            @Valid @RequestBody ReservationRequest request,
            Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            Reservation reservation = reservationService.createReservation(
                    userEmail, 
                    request.getBookId(), 
                    request.getDays()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    public ResponseEntity<List<Reservation>> getMyReservations(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(reservationService.getUserReservations(userEmail));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    @PatchMapping("/{id}/return")
    public ResponseEntity<Reservation> returnBook(@PathVariable Integer id) {
        return ResponseEntity.ok(reservationService.returnBook(id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // Error response class
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}