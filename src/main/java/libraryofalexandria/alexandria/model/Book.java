package libraryofalexandria.alexandria.model;

import jakarta.persistence.*;

@Entity  // Marks this class as a database entity (table)
@Table(name = "books") // Defines the table name
public class Book {

    @Id  // Marks this field as the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments ID
    private Long id;

    private String title;  
    private String category;
    private boolean readStatus;
    private String location;
    private String notes;

    // Default constructor (required by JPA)
    public Book() {}

    // Constructor with parameters
    public Book(String title, String category, boolean readStatus, String location, String notes) {
        this.title = title;
        this.category = category;
        this.readStatus = readStatus;
        this.location = location;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
