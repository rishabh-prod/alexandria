package libraryofalexandria.alexandria.service;

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
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    
    public Book addBook(Book book){
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
            .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }
    public Book deleteBook(Long id) {
        return bookRepository.findById(id)
            .map(book -> {
                bookRepository.delete(book); // Delete the book
                return book; // Return the deleted book details
            })
            .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }

    public List<Book> getBooksByCategory(String category){
        return bookRepository.findByCategory(category);
    }

    public List<Book> searchBooks(String title, String category) {
        return bookRepository.searchBooks(title, category);
    }
}
