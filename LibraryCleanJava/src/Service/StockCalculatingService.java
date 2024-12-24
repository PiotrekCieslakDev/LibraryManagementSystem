package Service;

import DomainHelpers.BooksStockWithAvailability;
import Domains.Book;
import Domains.BooksStock;
import Domains.Customer;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;

import java.util.ArrayList;
import java.util.List;

public class StockCalculatingService {
    private final IBooksStockDAL booksStockDAL;
    private final ICustomerDAL customerDAL;

    // Constructor
    public StockCalculatingService(IBooksStockDAL booksStockDAL, ICustomerDAL customerDAL) {
        this.booksStockDAL = booksStockDAL;
        this.customerDAL = customerDAL;
    }

    // Method to return a list of BooksStockWithAvailability objects, including available quantity
    public List<BooksStockWithAvailability> getBooksStockWithAvailability() {
        List<BooksStock> allBooksStock = booksStockDAL.getAllBooksStocks();
        List<BooksStockWithAvailability> booksStockWithAvailabilityList = new ArrayList<>();

        for (BooksStock booksStock : allBooksStock) {
            // Get the number of books rented by customers for this specific book
            int rentedBooks = getRentedBooksCount(booksStock);

            // Calculate available books by subtracting rented books from total stock
            int availableQuantity = booksStock.get_quantity() - rentedBooks;

            // Ensure availableQuantity is not negative
            availableQuantity = Math.max(availableQuantity, 0);

            // Create a BooksStockWithAvailability object with the available quantity
            BooksStockWithAvailability booksStockWithAvailability = new BooksStockWithAvailability(booksStock, availableQuantity);

            // Add the object to the list
            booksStockWithAvailabilityList.add(booksStockWithAvailability);
        }

        return booksStockWithAvailabilityList;
    }


    // Method to calculate the number of rented books
    public int calculateRentedBooks() {
        int rentedBooks = 0;

        List<Customer> allCustomers = customerDAL.getAllCustomers();
        for (Customer customer : allCustomers) {
            rentedBooks += customer.get_borrowedBooks().size();
        }

        return rentedBooks;
    }

    // Helper method to calculate how many books have been rented for a specific book
    private int getRentedBooksCount(BooksStock booksStock) {
        int rentedCount = 0;

        List<Customer> allCustomers = customerDAL.getAllCustomers();
        for (Customer customer : allCustomers) {
            for (Book borrowedBook : customer.get_borrowedBooks()) {
                if (borrowedBook.equals(booksStock.get_book())) {
                    rentedCount++;
                }
            }
        }

        return rentedCount;
    }
}
