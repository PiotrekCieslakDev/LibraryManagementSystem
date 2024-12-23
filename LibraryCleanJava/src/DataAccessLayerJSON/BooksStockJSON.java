package DataAccessLayerJSON;

import Domains.Book;
import Domains.BooksStock;
import Interfaces.IBooksStockDAL;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BooksStockJSON implements IBooksStockDAL {
    private final File file = new File(getClass().getClassLoader().getResource("data.json").getFile());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static BookJSON bookJSON;
    private List<BooksStock> booksStocks;

    public BooksStockJSON() {
        bookJSON = new BookJSON();
        this.booksStocks = loadBooksStocksFromFile();
    }

    @Override
    public Optional<BooksStock> getBooksStockById(UUID id) {
        return loadBooksStocksFromFile().stream()
                .filter(stock -> stock.get_id().equals(id))
                .findFirst();
    }

    @Override
    public Optional<BooksStock> getBooksStockByBooksId(UUID bookId) {
        loadBooksStocksFromFile();
        return booksStocks.stream()
                .filter(stock -> stock.get_book().get_id().equals(bookId))
                .findFirst();
    }

    @Override
    public List<BooksStock> getAllBooksStocks() {
        return loadBooksStocksFromFile();
    }

    @Override
    public boolean createBooksStock(BooksStock booksStock) {
        if (booksStocks.stream().noneMatch(stock -> stock.get_id().equals(booksStock.get_id()))) {
            booksStocks.add(booksStock);
            return saveToFile();
        }
        return false;
    }

    @Override
    public boolean updateBooksStock(BooksStock updatedBooksStock) {
        loadBooksStocksFromFile();
        for (int i = 0; i < booksStocks.size(); i++) {
            if (booksStocks.get(i).get_id().equals(updatedBooksStock.get_id())) {
                booksStocks.set(i, updatedBooksStock);
                return saveToFile();
            }
        }
        return false;
    }

    @Override
    public boolean deleteBooksStock(UUID id) {
        if (booksStocks.removeIf(stock -> stock.get_id().equals(id))) {
            return saveToFile();
        }
        return false;
    }

    // Private method to load all books stocks from JSON
    private List<BooksStock> loadBooksStocksFromFile() {
        if (!file.exists()) {
            System.out.println("Data file not found. Initializing empty books stock list.");
            return new ArrayList<>();
        }
        try {
            // Read the data from the file
            LibraryData libraryData = objectMapper.readValue(file, LibraryData.class);

            // Check if the stock is null or empty
            if (libraryData.getStock() == null) {
                System.out.println("Books stock data is null. Initializing empty list.");
                return new ArrayList<>();
            }

            // Fetch all books once into a map to avoid repeated lookups
            Map<UUID, Book> booksMap = bookJSON.getAllBooks().stream()
                    .collect(Collectors.toMap(Book::get_id, Function.identity()));

            // List to hold the mapped BooksStock objects
            List<BooksStock> mappedBooksStocks = new ArrayList<>();

            // Iterate over the books stock and map bookId to the full Book object
            for (BooksStock stock : libraryData.getStock()) {
                UUID bookId = stock.get_bookId();
                Book book = booksMap.get(bookId); // Fetch the full Book from the map

                if (book != null) {
                    // Set the full Book object in the stock
                    stock.set_book(book);

                    // Add the mapped BooksStock to the list
                    mappedBooksStocks.add(stock);
                } else {
                    // Optionally, log if book is missing
                    System.out.println("Book with ID " + bookId + " not found for stock entry.");
                }
            }

            booksStocks = mappedBooksStocks;
            return mappedBooksStocks; // Return the list with mapped BooksStock objects
        } catch (IOException e) {
            System.err.println("Error reading books stock data from file: " + e.getMessage());
            e.printStackTrace();

            return new ArrayList<>();
        }
    }



    // Private method to save all books stocks back to JSON
    private boolean saveToFile() {
        try {
            LibraryData libraryData = objectMapper.readValue(file, LibraryData.class);
            libraryData.setStock(booksStocks);
            objectMapper.writeValue(file, libraryData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
