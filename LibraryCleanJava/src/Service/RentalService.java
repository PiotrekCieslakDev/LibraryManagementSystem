package Service;

import Domains.Book;
import Domains.BooksStock;
import Domains.Customer;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        // Check if book is in the stock db
        Optional<BooksStock> stock = booksStockDAL.getBooksStockByBooksId(book.get_id());

        // Check if bookstock exists and quantity is more than 0
        if (stock.isEmpty() || stock.get().get_quantity() <= 0) {
            System.out.println("Book is out of stock.");
            return false;
        }

        // Check if the customer has already borrowed the book
        if (customer.get_borrowedBooks().stream()
                .anyMatch(b -> b.get_id().equals(book.get_id()))) {
            System.out.println("Customer already borrowed this book.");
            return false;
        }

        // Add book to the customer's borowed books
        customer.get_borrowedBooks().add(book);
        customerDAL.updateCustomer(customer);

        // Decrease the book quantity
//        stock.get().set_quantity(stock.get().get_quantity() - 1);
//        booksStockDAL.updateBooksStock(stock.get());

        System.out.println("Book successfully borrowed.");
        return true;
    }

    public boolean returnBook(Customer customer, Book book) {
        // Check if the customer borrowed the book
        boolean hasBorrowed = customer.get_borrowedBooks().removeIf(b -> b.get_id().equals(book.get_id()));
        if (!hasBorrowed) {
            System.out.println("Customer did not borrow this book.");
            return false;
        }

        // Update client's info
        customerDAL.updateCustomer(customer);

        //Increase bookstock of the book
//        Optional<BooksStock> stock = booksStockDAL.getBooksStockByBooksId(book.get_id());
//        if (stock != null) {
//            stock.get().set_quantity(stock.get().get_quantity() + 1);
//            booksStockDAL.updateBooksStock(stock.get());
//        } else {
//            System.out.println("Error: Stock record not found.");
//            return false;
//        }

        System.out.println("Book successfully returned.");
        return true;
    }

    public List<Customer> GetCustomersWhoBorrowedTheBookByBookId(UUID bookId) {
        List<Customer> customersWhoBorrowedTheBook = new ArrayList<Customer>();

        for(Customer customer : customerDAL.getAllCustomers()){
            if(customer.get_borrowedBooks().stream().anyMatch(b -> b.get_id().equals(bookId))){
                customersWhoBorrowedTheBook.add(customer);
            }
        }
        return customersWhoBorrowedTheBook;
    }
}
