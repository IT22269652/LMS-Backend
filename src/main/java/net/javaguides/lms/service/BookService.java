package net.javaguides.lms.service;

import net.javaguides.lms.entity.Book;
import net.javaguides.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Book createBook(Book book) {
        book.setStatus(Book.BookStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setGenre(bookDetails.getGenre());
        book.setLanguage(bookDetails.getLanguage());
        book.setIsbn(bookDetails.getIsbn());
        book.setImageUrl(bookDetails.getImageUrl());
        book.setCategory(bookDetails.getCategory());
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public Book updateBookStatus(Integer id, Book.BookStatus status) {
        Book book = getBookById(id);
        book.setStatus(status);
        return bookRepository.save(book);
    }
}