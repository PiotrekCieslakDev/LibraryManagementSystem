package Interfaces;

import Domains.Book;
import Domains.BooksStock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


//Interface for Book Stock database access, all methods are inherently abstract as it is an interface
public interface IBooksStockDAL {
    //Retrievers
    public Optional<BooksStock> getBooksStockById(UUID id);
    public Optional<BooksStock> getBooksStockByBooksId(UUID bookId);
    public List<BooksStock> getAllBooksStocks();

    //Abstract create book stock method
    public boolean createBooksStock(BooksStock booksStock);

    //Abstract update book stock method
    public boolean updateBooksStock(BooksStock updatedBooksStock);

    //Abstract delete book stock method
    public boolean deleteBooksStock(UUID id);
}
