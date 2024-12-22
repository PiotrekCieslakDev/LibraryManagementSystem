package Service;

import Domains.Book;
import Domains.BooksStock;
import Domains.Customer;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;

import javax.swing.text.html.Option;
import java.util.Optional;

public class RentalService {
    private final IBookDAL bookDAL;
    private final IBooksStockDAL booksStockDAL;
    private final ICustomerDAL customerDAL;

    public RentalService(IBookDAL bookDAL, IBooksStockDAL booksStockDAL, ICustomerDAL customerDAL) {
        this.bookDAL = bookDAL;
        this.booksStockDAL = booksStockDAL;
        this.customerDAL = customerDAL;
    }

    public boolean borrowBook(Customer customer, Book book) {
        //Check if book is in the Database
        Optional<Book> foundBook = bookDAL.getAllBooks().stream()
                .filter(b -> b.get_id().equals(book.get_id()))
                .findFirst();

        if (foundBook.isEmpty()) {
            System.out.println("Book not found.");
            return false;
        }

        // Sprawdź, czy książka jest dostępna w magazynie
        Optional<BooksStock> stock = booksStockDAL.getBooksStockByBooksId(book.get_id());

        // Sprawdzamy, czy BooksStock istnieje i czy ilość jest większa niż 0
        if (stock.isEmpty() || stock.get().get_quantity() <= 0) {
            System.out.println("Book is out of stock.");
            return false;
        }


        // Sprawdź, czy klient już wypożyczył tę książkę
        if (customer.get_boroowedBooks().stream()
                .anyMatch(b -> b.get_id().equals(book.get_id()))) {
            System.out.println("Customer already borrowed this book.");
            return false;
        }

        // Dodaj książkę do listy wypożyczeń klienta
        customer.get_boroowedBooks().add(book);
        customerDAL.updateCustomer(customer);

        // Zmniejsz ilość książek w magazynie
        stock.get().set_quantity(stock.get().get_quantity() - 1);
        booksStockDAL.updateBooksStock(stock.get());

        System.out.println("Book successfully borrowed.");
        return true;
    }

    public boolean returnBook(Customer customer, Book book) {
        // Sprawdź, czy klient wypożyczył tę książkę
        boolean hasBorrowed = customer.get_boroowedBooks().removeIf(b -> b.get_id().equals(book.get_id()));
        if (!hasBorrowed) {
            System.out.println("Customer did not borrow this book.");
            return false;
        }

        // Zaktualizuj dane klienta
        customerDAL.updateCustomer(customer);

        // Zwiększ ilość książek w magazynie
        Optional<BooksStock> stock = booksStockDAL.getBooksStockByBooksId(book.get_id());
        if (stock != null) {
            stock.get().set_quantity(stock.get().get_quantity() + 1);
            booksStockDAL.updateBooksStock(stock.get());
        } else {
            System.out.println("Error: Stock record not found.");
            return false;
        }

        System.out.println("Book successfully returned.");
        return true;
    }
}
