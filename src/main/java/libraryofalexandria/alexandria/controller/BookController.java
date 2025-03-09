package libraryofalexandria.alexandria.controller;

import libraryofalexandria.alexandria.model.Book;
import libraryofalexandria.alexandria.service.BookService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;



@RestController
@RequestMapping("/books") // Base URL for this controller
public class BookController {
    private final BookService bookService;

    // Constructor-based dependency injection
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET request to fetch all books
    @GetMapping
    public Page<Book> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        return bookService.getAllBooks(page, size, sortBy, sortDirection);
    }
    

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.findById(id);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(book); // Sends 200 OK response
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book deletedBook = bookService.deleteBook(id);
        return ResponseEntity.ok(deletedBook);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean read,
            @RequestParam(required = false) String notes) {

        List<Book> books = bookService.searchBooks(title, category, status, read, notes);
        return ResponseEntity.ok(books);
    }
}
