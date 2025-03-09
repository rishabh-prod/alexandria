package libraryofalexandria.alexandria.service;

import libraryofalexandria.alexandria.exception.BookNotFoundException;
import libraryofalexandria.alexandria.exception.InvalidBookException;
import libraryofalexandria.alexandria.exception.InvalidSearchCriteriaException;
import libraryofalexandria.alexandria.model.Book;
import libraryofalexandria.alexandria.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service // Marks this class as a service component
public class BookService {
    private final BookRepository bookRepository;

    // Constructor-based dependency injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Method to fetch all books
    public Page<Book> getAllBooks(Integer page, Integer size, String sortBy, String sortDirection) {
        int defaultPage = (page != null) ? page : 0;
        int defaultSize = (size != null) ? size : 10;
        String defaultSortBy = (sortBy != null) ? sortBy : "title"; // Default sorting by title
        Sort.Direction direction = (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
    
        Pageable pageable = PageRequest.of(defaultPage, defaultSize, Sort.by(direction, defaultSortBy));
        return bookRepository.findAll(pageable);
    }
    
    

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }
    
    public Book addBook(Book book) {
    if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
        throw new InvalidBookException("Title cannot be empty");
    }
    if (book.getCategory() == null || book.getCategory().trim().isEmpty()) {
        throw new InvalidBookException("Category cannot be empty");
    }
    return bookRepository.save(book);
}

    // Service method to update a book's details
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
            .map(book -> {
                // Update the fields with new values
                book.setTitle(updatedBook.getTitle());
                book.setCategory(updatedBook.getCategory());
                book.setLocation(updatedBook.getLocation());
                book.setReadStatus(updatedBook.isReadStatus());
                book.setNotes(updatedBook.getNotes());
                return bookRepository.save(book); // Save updated book
            })
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }
    public Book deleteBook(Long id) {
        return bookRepository.findById(id)
            .map(book -> {
                bookRepository.delete(book); // Delete the book
                return book; // Return the deleted book details
            })
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }

    public List<Book> getBooksByCategory(String category){
        return bookRepository.findByCategory(category);
    }

    public List<Book> searchBooks(String title, String category, String status, Boolean read, String notes) {
        if ((title == null || title.trim().isEmpty()) &&
            (category == null || category.trim().isEmpty()) &&
            (status == null || status.trim().isEmpty()) &&
            read == null &&
            (notes == null || notes.trim().isEmpty())) {
            throw new InvalidSearchCriteriaException("At least one search parameter must be provided.");
        }
    
        return bookRepository.searchBooks(title, category, status, read, notes);
    }
    
}
    