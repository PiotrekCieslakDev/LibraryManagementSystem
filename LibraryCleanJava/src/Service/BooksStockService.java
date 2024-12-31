package Service;

import DataAccessLayer.BookDAL;
import Domains.BooksStock;
import Domains.Customer;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BooksStockService {
    private final IBooksStockDAL booksStockDAL;
    private final IBookDAL bookDAL;
    private final ICustomerDAL customerDAL;

    public BooksStockService(IBooksStockDAL booksStockDAL, IBookDAL bookDAL, ICustomerDAL customerDAL) {
        this.booksStockDAL = booksStockDAL;
        this.bookDAL = bookDAL;
        this.customerDAL = customerDAL;
    }

    // Create a new book stock record
    public boolean createBooksStock(BooksStock booksStock) {
        //        Optional<BooksStock> booksStockPresent = getBooksStockByBooksId(bookId);
//
//        if(booksStockPresent.isPresent()){
//            BooksStock booksStock = booksStockPresent.get();
//            System.out.println("This book (" + booksStock.get_book().get_title() + ") already has stock");
//            return false;
//        }
        booksStock.set_bookId(booksStock.get_book().get_id());
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
        return booksStockDAL.updateBooksStock(updatedBooksStock);
    }

    // Delete a book stock record by ID
    public boolean deleteBooksStock(UUID id) {

        try{
            if(booksStockDAL.deleteBooksStock(id)){
                if(bookDAL.deleteBook(id)){
                    return true;
                }
            }
        }
        catch(Exception e){
            return false;
        }
        return false;
    }

    public List<Customer> getRentersByBookId(UUID bookId) {
        List<Customer> renters = new ArrayList<>(); // Create a list to hold the renters

        // Iterate over all customers
        for (Customer customer : customerDAL.getAllCustomers()) {
            // Check if the customer has rented a book with the specified bookId
            if (customer.get_borrowedBooks().stream().anyMatch(book -> book.get_id().equals(bookId))) {
                renters.add(customer); // Add the customer to the renters list
            }
        }

        return renters; // Return the list of renters
    }


}
