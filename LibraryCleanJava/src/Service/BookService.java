package Service;

import DataAccessLayer.BookDAL;
import Domains.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookService {
    private final BookDAL bookDAL;

    public BookService(BookDAL bookDAL) {
        this.bookDAL = bookDAL;
    }

    // Add a new book
    public boolean createBook(String title, String author, String publisher, int year) {
        Book book = new Book(title, author, publisher, year);
        return bookDAL.createBook(book);
    }

    // Retrieve a book by ID
    public Optional<Book> getBookById(UUID id) {
        return bookDAL.getBookById(id);
    }

    // Retrieve all books
    public List<Book> getAllBooks() {
        return bookDAL.getAllBooks();
    }

    // Update an existing book
    public boolean updateBook(UUID id, String title, String author, String publisher, int year) {
        // Retrieve book from DAL, can be optional (null)
        Optional<Book> bookOpt = bookDAL.getBookById(id);
        if (bookOpt.isPresent()) {
            Book updatedBook = new Book(id, title, author, publisher, year);
            return bookDAL.updateBook(updatedBook);
        }
        return false;
    }

    // Delete a book by ID
    public boolean deleteBook(UUID id) {
        return bookDAL.deleteBook(id);
    }

    public List<Book> searchBooks(Map<String, Object> searchParams) {
        return bookDAL.getAllBooks().stream()
                .filter(book -> {
                    boolean matches = true;
                    if (searchParams.containsKey("title")) {
                        matches = matches && book.get_title().toLowerCase().contains(((String) searchParams.get("title")).toLowerCase());
                    }
                    if (searchParams.containsKey("author")) {
                        matches = matches && book.get_author().toLowerCase().contains(((String) searchParams.get("author")).toLowerCase());
                    }
                    if (searchParams.containsKey("publisher")) {
                        matches = matches && book.get_publisher().toLowerCase().contains(((String) searchParams.get("publisher")).toLowerCase());
                    }
                    if (searchParams.containsKey("year")) {
                        matches = matches && book.get_year() == (Integer) searchParams.get("year");
                    }
                    return matches;
                })
                .collect(Collectors.toList());
    }

}
