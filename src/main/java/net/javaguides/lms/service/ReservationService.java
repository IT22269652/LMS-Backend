package net.javaguides.lms.service;

import net.javaguides.lms.entity.Book;
import net.javaguides.lms.entity.Reservation;
import net.javaguides.lms.entity.User;
import net.javaguides.lms.repository.BookRepository;
import net.javaguides.lms.repository.ReservationRepository;
import net.javaguides.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Reservation createReservation(String userEmail, Integer bookId, Integer days) {
        // Get user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user is blacklisted
        if (user.getIsBlacklisted()) {
            throw new RuntimeException("Cannot reserve book. User is blacklisted.");
        }

        // Get book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // Check if book is available
        if (book.getStatus() != Book.BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for reservation");
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setDueDate(LocalDate.now().plusDays(days));
        reservation.setStatus(Reservation.ReservationStatus.ACTIVE);

        // Update book status
        book.setStatus(Book.BookStatus.RESERVED);
        bookRepository.save(book);

        // Save reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Send confirmation email
        try {
            emailService.sendReservationConfirmation(
                    user.getEmail(),
                    book.getTitle(),
                    reservation.getDueDate().toString()
            );
        } catch (Exception e) {
            System.err.println("Failed to send reservation confirmation email: " + e.getMessage());
        }

        return savedReservation;
    }

    public List<Reservation> getUserReservations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUserId(user.getId());
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    @Transactional
    public Reservation returnBook(Integer id) {
        Reservation reservation = getReservationById(id);

        if (reservation.getStatus() == Reservation.ReservationStatus.RETURNED) {
            throw new RuntimeException("Book has already been returned");
        }

        // Update reservation status
        reservation.setStatus(Reservation.ReservationStatus.RETURNED);

        // Update book status
        Book book = reservation.getBook();
        book.setStatus(Book.BookStatus.AVAILABLE);
        bookRepository.save(book);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }
}