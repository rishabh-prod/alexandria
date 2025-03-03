package libraryofalexandria.alexandria.repository;

import libraryofalexandria.alexandria.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.domain.Sort;


@Repository // Marks this interface as a Spring-managed repository
public interface BookRepository extends JpaRepository<Book, Long>{
    // JpaRepository provides built-in CRUD operations (Create, Read, Update, Delete)
    // Custom method to find books by category
    List<Book> findByCategory(String category); 
    @Query("SELECT b FROM Book b WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND (:category IS NULL OR LOWER(b.category) LIKE LOWER(CONCAT('%', :category, '%')))")
    List<Book> searchBooks(@Param("title") String title, @Param("category") String category);    
}
