package InitializeHelper;

import Domains.Book;
import Domains.BooksStock;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;

import java.util.List;
import java.util.Random;

public class BooksStockInitializer {
    private final IBookDAL _bookDAL;
    private final IBooksStockDAL _booksStockDAL;

    public BooksStockInitializer(IBookDAL bookDAL, IBooksStockDAL booksStockDAL) {
        _bookDAL = bookDAL;
        _booksStockDAL = booksStockDAL;
        InitalizeBooksStock();
    }

    private void InitalizeBooksStock(){
        _bookDAL.getAllBooks().forEach(book -> {
            Random random = new Random();
            int quantity = random.nextInt(16) + 5; // Generates a number between 5 and 20
            BooksStock booksStock = new BooksStock(book, quantity);
            _booksStockDAL.createBooksStock(booksStock);
        });
    }
}
