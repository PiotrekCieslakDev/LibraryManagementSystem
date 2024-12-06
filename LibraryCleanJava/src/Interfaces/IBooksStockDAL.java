package Interfaces;

import Domains.Book;
import Domains.BooksStock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBooksStockDAL {
    //Retriever
    public Optional<BooksStock> getBooksStockById(UUID id);
    public Optional<BooksStock> getBooksStockByBooksId(UUID bookId);
    public List<BooksStock> getAllBooksStocks();

    //Create
    public boolean createBooksStock(BooksStock booksStock);

    //Update
    public boolean updateBooksStock(BooksStock updatedBooksStock);

    //Delete
    public boolean deleteBooksStock(UUID id);
}
