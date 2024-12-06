package DataAccessLayer;

import Domains.BooksStock;
import Interfaces.IBooksStockDAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BooksStockDAL implements IBooksStockDAL {
    private final List<BooksStock> booksStockList;

    public BooksStockDAL() {
        this.booksStockList = new ArrayList<>();
    }

    // Create a new book stock record
    @Override
    public boolean createBooksStock(BooksStock booksStock) {
        booksStockList.add(booksStock);
        return true;
    }

    // Retrieve a book stock record by ID
    @Override
    public Optional<BooksStock> getBooksStockById(UUID id) {
        return booksStockList.stream()
                .filter(stock -> stock.get_id().equals(id))
                .findFirst();
    }

    @Override
    public Optional<BooksStock> getBooksStockByBooksId(UUID bookId){
        return booksStockList.stream()
                .filter(stock -> stock.get_book().get_id().equals(bookId))
                .findFirst();
    }

    // Retrieve all book stock records
    @Override
    public List<BooksStock> getAllBooksStocks() {
        return new ArrayList<>(booksStockList);
    }

    // Update an existing book stock record
    @Override
    public boolean updateBooksStock(BooksStock updatedBooksStock) {
        for (int i = 0; i < booksStockList.size(); i++) {
            if (booksStockList.get(i).get_id().equals(updatedBooksStock.get_id())) {
                booksStockList.set(i, updatedBooksStock);
                return true;
            }
        }
        return false;
    }

    // Delete a book stock record by ID
    @Override
    public boolean deleteBooksStock(UUID id) {
        return booksStockList.removeIf(stock -> stock.get_id().equals(id));
    }
}
