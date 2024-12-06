package Service;

import Domains.BooksStock;
import Interfaces.IBooksStockDAL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BooksStockService {
    private final IBooksStockDAL booksStockDAL;

    public BooksStockService(IBooksStockDAL booksStockDAL) {
        this.booksStockDAL = booksStockDAL;
    }

    // Create a new book stock record
    public boolean createBooksStock(BooksStock booksStock) {
        return booksStockDAL.createBooksStock(booksStock);
    }

    // Retrieve a book stock record by ID
    public Optional<BooksStock> getBooksStockById(UUID id) {
        return booksStockDAL.getBooksStockById(id);
    }

    public Optional<BooksStock> getBooksStockByBooksId(UUID booksId){
        return booksStockDAL.getBooksStockByBooksId(booksId);
    }

    // Retrieve all book stock records
    public List<BooksStock> getAllBooksStocks() {
        return booksStockDAL.getAllBooksStocks();
    }

    // Update an existing book stock record
    public boolean updateBooksStock(UUID bookId, BooksStock updatedBooksStock)
    {
        Optional<BooksStock> booksStockPresent = getBooksStockByBooksId(bookId);

        if(booksStockPresent.isPresent()){
            BooksStock booksStock = booksStockPresent.get();
            System.out.println("This book (" + booksStock.get_book().get_title() + ") already has stock");
            return false;
        }
        return booksStockDAL.updateBooksStock(updatedBooksStock);
    }

    // Delete a book stock record by ID
    public boolean deleteBooksStock(UUID id) {
        return booksStockDAL.deleteBooksStock(id);
    }
}
