package DataAccessLayer;

import Domains.Book;
import Interfaces.IBookDAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookDAL implements IBookDAL {
    private final List<Book> books;

    public BookDAL() {
        this.books = new ArrayList<>();
    }

    // Add a new book
    public boolean createBook(Book book) {
        books.add(book);
        return true;
    }

    // Retrieve a book by ID
    public Optional<Book> getBookById(UUID id) {
        return books.stream()
                .filter(book -> book.get_id().equals(id))
                .findFirst();
    }

    // Retrieve all books
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // Update an existing book
    public boolean updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).get_id().equals(updatedBook.get_id())) {
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    // Delete a book by ID
    public boolean deleteBook(UUID id) {
        return books.removeIf(book -> book.get_id().equals(id));
    }
}
