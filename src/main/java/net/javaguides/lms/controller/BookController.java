package net.javaguides.lms.controller;

import jakarta.validation.Valid;
import net.javaguides.lms.dto.BookDTO;
import net.javaguides.lms.entity.Book;
import net.javaguides.lms.entity.Category;
import net.javaguides.lms.service.BookService;
import net.javaguides.lms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setLanguage(bookDTO.getLanguage());
        book.setIsbn(bookDTO.getIsbn());
        book.setImageUrl(bookDTO.getImageUrl());
        
        if (bookDTO.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(bookDTO.getCategoryId());
            book.setCategory(category);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(book));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @Valid @RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setLanguage(bookDTO.getLanguage());
        book.setIsbn(bookDTO.getIsbn());
        book.setImageUrl(bookDTO.getImageUrl());
        
        if (bookDTO.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(bookDTO.getCategoryId());
            book.setCategory(category);
        }
        
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Book> updateBookStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        try {
            Book.BookStatus bookStatus = Book.BookStatus.valueOf(status.toUpperCase());
            return ResponseEntity.ok(bookService.updateBookStatus(id, bookStatus));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status. Must be AVAILABLE or RESERVED");
        }
    }
}