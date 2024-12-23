package DataAccessLayerJSON;

import Domains.Book;
import Interfaces.IBookDAL;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookJSON implements IBookDAL {
    private final File file = new File(getClass().getClassLoader().getResource("data.json").getFile());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Book> books;

    public BookJSON() {
        this.books = loadBooksFromFile();
    }

    @Override
    public Optional<Book> getBookById(UUID id) {
        return books.stream()
                .filter(book -> book.get_id().equals(id))
                .findFirst();
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public boolean createBook(Book book) {
        if (books.stream().noneMatch(b -> b.get_id().equals(book.get_id()))) {
            books.add(book);
            return saveToFile();
        }
        return false;
    }

    @Override
    public boolean updateBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).get_id().equals(book.get_id())) {
                books.set(i, book);
                return saveToFile();
            }
        }
        return false;
    }

    @Override
    public boolean deleteBook(UUID id) {
        if (books.removeIf(book -> book.get_id().equals(id))) {
            return saveToFile();
        }
        return false;
    }

    // Private method to load all books from JSON
    private List<Book> loadBooksFromFile() {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            LibraryData libraryData = objectMapper.readValue(file, LibraryData.class);
            return libraryData.getBooks();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Private method to save all books back to JSON
    private boolean saveToFile() {
        try {
            LibraryData libraryData = objectMapper.readValue(file, LibraryData.class);
            libraryData.setBooks(books);
            objectMapper.writeValue(file, libraryData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}