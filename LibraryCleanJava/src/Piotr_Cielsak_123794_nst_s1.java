import DependencyInitializerHelper.DependencyHelper;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;
import Service.*;
import Domains.*;

import java.util.*;

public class Piotr_Cielsak_123794_nst_s1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //READ BEFORE USING THIS CLASS
        //Exception handlers and annotation checks are not implemented in this class! They are implemented in the deeper layers in the name of reusability

        // Initialize the DALs
        IBookDAL bookDAL = DependencyHelper.bookDAL;
        IBooksStockDAL booksStockDAL = DependencyHelper.booksStockDAL;
        ICustomerDAL customerDAL = DependencyHelper.customerDAL;

        // Initialize the services
        BookService bookService = new BookService(bookDAL);
        BooksStockService booksStockService = new BooksStockService(booksStockDAL, bookDAL, customerDAL);
        CustomerService customerService = new CustomerService(customerDAL);
        StockCalculatingService stockCalculatingService = new StockCalculatingService(booksStockDAL, customerDAL);
        RentalService rentalService = new RentalService(bookDAL, booksStockDAL, customerDAL);


        // Initialize hardcoded data
        // Use only if you use any other DB than hardcoded one
        //new BooksInitializer(bookDAL);
        //new BooksStockInitializer(bookDAL, booksStockDAL);
        //new CustomersInitializer(customerDAL);

        //Open loop for the entire application
        //Start main menu
        while (true) {
            System.out.println("Library Management system by Piotr Cieslak 123794 nst s1");
            System.out.println("Library Management System");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Stocks");
            System.out.println("3. Manage Customers");
            System.out.println("4. Rental Management System");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            //Open scanner for consuming user input
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume new line

            //Open switchbox for main menu options
            switch (option) {
                case 1:
                    manageBooks(scanner, bookService, booksStockService);
                    break;
                case 2:
                    manageStocks(scanner, bookService, booksStockService, stockCalculatingService);
                    break;
                case 3:
                    manageCustomers(scanner, customerService);
                    break;
                case 4:
                    rentalManagementSystem(scanner, rentalService, customerService, bookService);
                    break;
                case 5:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //Method that implements managing books option into user interface
    private static void manageBooks(Scanner scanner, BookService bookService, BooksStockService booksStockService) {
        while (true) {
            System.out.println("\nBook Management");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Books");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            //Switch options for managing books
            switch (option) {
                case 1:
                    // Add Book method
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publisher: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    //method for creating book into the system
                    bookService.createBook(title, author, publisher, year);
                    System.out.println("Book added successfully.");
                    break;

                case 2:
                    // View All Books method
                    System.out.println("Books in the Library:");
                    //method for retrieving all books
                    bookService.getAllBooks().forEach(book -> System.out.println(book.GetBookToString()));
                    break;

                case 3:
                    // Update Book method
                    System.out.print("Enter the ID of the book to update: ");
                    UUID bookIdToUpdate = UUID.fromString(scanner.nextLine());
                    Optional<Book> optionalBook = bookService.getBookById(bookIdToUpdate);
                    if (optionalBook.isPresent()) {
                        Book bookToUpdate = optionalBook.get();
                        System.out.print("Enter new title (current: " + bookToUpdate.get_title() + "): ");
                        String newTitle = scanner.nextLine();
                        System.out.print("Enter new author (current: " + bookToUpdate.get_author() + "): ");
                        String newAuthor = scanner.nextLine();
                        System.out.print("Enter new publisher (current: " + bookToUpdate.get_publisher() + "): ");
                        String newPublisher = scanner.nextLine();
                        System.out.print("Enter new year (current: " + bookToUpdate.get_year() + "): ");
                        int newYear = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        //method for updating the given book
                        bookService.updateBook(bookIdToUpdate, newTitle, newAuthor, newPublisher, newYear);
                        System.out.println("Book updated successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 4:
                    // Delete Book method
                    System.out.print("Enter the ID of the book to delete: ");
                    UUID bookIdToDelete = UUID.fromString(scanner.nextLine());
                    if (bookService.deleteBook(bookIdToDelete)) {
                        System.out.println("Book deleted successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 5:
                    // Search Books method
                    System.out.println("Enter search parameters (leave blank to skip): ");
                    System.out.print("Title: ");
                    String searchTitle = scanner.nextLine();
                    System.out.print("Author: ");
                    String searchAuthor = scanner.nextLine();
                    System.out.print("Publisher: ");
                    String searchPublisher = scanner.nextLine();
                    System.out.print("Year: ");
                    String yearInput = scanner.nextLine();
                    Integer searchYear = null;
                    if (!yearInput.isEmpty()) {
                        searchYear = Integer.valueOf(yearInput);
                    }

                    // Create a map to hold the search parameters
                    Map<String, Object> searchParams = new HashMap<>();
                    if (!searchTitle.isEmpty()) {
                        searchParams.put("title", searchTitle);
                    }
                    if (!searchAuthor.isEmpty()) {
                        searchParams.put("author", searchAuthor);
                    }
                    if (!searchPublisher.isEmpty()) {
                        searchParams.put("publisher", searchPublisher);
                    }
                    if (searchYear != null) {
                        searchParams.put("year", searchYear);
                    }

                    // Perform search and display results
                    List<Book> foundBooks = bookService.searchBooks(searchParams);
                    if (foundBooks.isEmpty()) {
                        System.out.println("No books found with the given parameters.");
                    } else {
                        System.out.println("Found Books:");
                        for (Book book : foundBooks) {
                            System.out.println(book.GetBookToString());
                        }
                    }
                    break;

                case 6:
                    return; // Back to main menu

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void manageStocks(Scanner scanner, BookService bookService, BooksStockService booksStockService, StockCalculatingService stockCalculatingService) {
        while (true) {
            System.out.println("\nStock Management");
            System.out.println("1. Add Stock");
            System.out.println("2. View All Stocks");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Stock");
            System.out.println("5. Search Stock by Book ID");
            System.out.println("6. Search book's renters by book ID");
            System.out.println("7. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    // Add Stock
                    System.out.print("Enter Book ID: ");
                    UUID bookId = UUID.fromString(scanner.nextLine());
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Optional<Book> optionalBook = bookService.getBookById(bookId);
                    if (optionalBook.isPresent()) {
                        System.out.println(optionalBook.get().GetBookToString());
                        BooksStock newStock = new BooksStock(optionalBook.get(), quantity);
                        booksStockService.createBooksStock(newStock);
                        System.out.println("Stock added successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 2:
                    // View All Stocks
                    System.out.println("Books Stock in the Library:");
                    stockCalculatingService.getBooksStockWithAvailability().forEach(stock -> {
                        System.out.println("Book: " + stock.get_book().GetBookToString() + " || Quantity: " + stock.get_quantity() + " || Available quantity: " + stock.get_availableQuantity());
                    });
//                    booksStockService.getAllBooksStocks().forEach(stock -> {
//                        System.out.println("Book: " + stock.get_book().GetBookToString() + " || Quantity: " + stock.get_quantity());
//                    });
                    break;

                case 3:
                    // Update Stock
                    System.out.print("Enter the ID of the book to update its stock: ");
                    UUID bookIdToUpdate = UUID.fromString(scanner.nextLine());
                    Optional<BooksStock> optionalStock = booksStockService.getBooksStockByBooksId(bookIdToUpdate);
                    if (optionalStock.isPresent()) {
                        BooksStock stockToUpdate = optionalStock.get();
                        System.out.print("Enter new quantity (current: " + stockToUpdate.get_quantity() + "): ");
                        int newQuantity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        stockToUpdate.set_quantity(newQuantity);
                        booksStockService.updateBooksStock(bookIdToUpdate, stockToUpdate);
                        System.out.println("Stock updated successfully.");
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;

                case 4:
                    // Delete Stock
                    System.out.print("Enter the ID of the book of which stock to delete: ");
                    UUID bookToDeleteId = UUID.fromString(scanner.nextLine());
                    Optional<Book> optionalBookToDelete = bookService.getBookById(bookToDeleteId);
                    if (optionalBookToDelete.isPresent()) {
                        Optional<BooksStock> booksStockToDelete = booksStockService.getBooksStockByBooksId(bookToDeleteId);
                        if(booksStockService.deleteBooksStock(booksStockToDelete.get().get_id())) {
                            System.out.println("Stock deleted successfully.");
                        }
                        else{
                            System.out.println("Stock not found.");
                        }
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 5:
                    // Search Stock by Book ID
                    System.out.print("Enter Book ID to search: ");
                    UUID searchBookId = UUID.fromString(scanner.nextLine());
                    Optional<BooksStock> foundStock = booksStockService.getBooksStockByBooksId(searchBookId);
                    if (foundStock.isPresent()) {
                        BooksStock stock = foundStock.get();
                        System.out.println("Found Stock: Book: " + stock.get_book().GetBookToString() + " || Quantity: " + stock.get_quantity());
                    } else {
                        System.out.println("No stock found for the specified Book ID.");
                    }
                    break;

                case 6:
                    // Search books renters by book ID
                    System.out.println("Enter book ID:");
                    UUID searchBookIdForRentersSearch = UUID.fromString(scanner.nextLine());
                    List<Customer> foundRenters = booksStockService.getRentersByBookId(searchBookIdForRentersSearch);
                    if (foundRenters.isEmpty()) {
                        System.out.println("No renters found for the specified Book ID.");
                    } else {
                        List<Customer> stock = foundRenters.stream().toList();
                        for (Customer customer : foundRenters) {
                            System.out.println("Found cusotmers who rented this book: " +  customer.getStructuredDataAboutCustomerForTerminalPrint());
                        }
                    }
                    break;
                case 7:
                    return; // Back to main menu

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageCustomers(Scanner scanner, CustomerService customerService) {
        while (true) {
            System.out.println("\nCustomer Management");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Search Customers");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    // Add Customer
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();

                    Customer newCustomer = new Customer(firstName, lastName, email, phone, new ArrayList<>());
                    customerService.createCustomer(newCustomer);
                    System.out.println("Customer added successfully. ID: " + newCustomer.get_id() );
                    break;

                case 2:
                    // View All Customers
                    System.out.println("Customers in the Library:");
                    customerService.getAllCustomers().forEach(customer -> {
                        System.out.println("Customer ID: " + customer.get_id());
                        System.out.println("Name: " + customer.get_firstName() + " " + customer.get_lastName());
                        System.out.println("Email: " + customer.get_email());
                        System.out.println("Phone: " + customer.get_phone());
                        System.out.println();
                    });
                    break;

                case 3:
                    // Update Customer
                    System.out.print("Enter the ID of the customer to update: ");
                    UUID customerIdToUpdate = UUID.fromString(scanner.nextLine());
                    Optional<Customer> optionalCustomer = customerService.getCustomerById(customerIdToUpdate);
                    if (optionalCustomer.isPresent()) {
                        Customer customerToUpdate = optionalCustomer.get();
                        System.out.print("Enter new first name (current: " + customerToUpdate.get_firstName() + "): ");
                        String newFirstName = scanner.nextLine();
                        System.out.print("Enter new last name (current: " + customerToUpdate.get_lastName() + "): ");
                        String newLastName = scanner.nextLine();
                        System.out.print("Enter new email (current: " + customerToUpdate.get_email() + "): ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Enter new phone (current: " + customerToUpdate.get_phone() + "): ");
                        String newPhone = scanner.nextLine();

                        customerToUpdate.set_firstName(newFirstName);
                        customerToUpdate.set_lastName(newLastName);
                        customerToUpdate.set_email(newEmail);
                        customerToUpdate.set_phone(newPhone);
                        customerService.updateCustomer(customerToUpdate);
                        System.out.println("Customer updated successfully.");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 4:
                    // Delete Customer
                    System.out.print("Enter the ID of the customer to delete: ");
                    UUID customerIdToDelete = UUID.fromString(scanner.nextLine());
                    if (customerService.deleteCustomer(customerIdToDelete)) {
                        System.out.println("Customer deleted successfully.");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case 5:
                    // Search Customers
                    System.out.println("Enter search parameters (leave blank to skip): ");
                    System.out.print("First Name: ");
                    String searchFirstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String searchLastName = scanner.nextLine();
                    System.out.print("Email: ");
                    String searchEmail = scanner.nextLine();
                    System.out.print("Phone: ");
                    String searchPhone = scanner.nextLine();

                    Map<String, String> searchParams = new HashMap<>();
                    if (!searchFirstName.isEmpty()) {
                        searchParams.put("firstName", searchFirstName);
                    }
                    if (!searchLastName.isEmpty()) {
                        searchParams.put("lastName", searchLastName);
                    }
                    if (!searchEmail.isEmpty()) {
                        searchParams.put("email", searchEmail);
                    }
                    if (!searchPhone.isEmpty()) {
                        searchParams.put("phone", searchPhone);
                    }

                    List<Customer> foundCustomers = customerService.searchCustomers(searchParams);
                    if (foundCustomers.isEmpty()) {
                        System.out.println("No customers found with the given parameters.");
                    } else {
                        System.out.println("Found Customers:");
                        for (Customer customer : foundCustomers) {
                            System.out.println("Customer ID: " + customer.get_id());
                            System.out.println("Name: " + customer.get_firstName() + " " + customer.get_lastName());
                            System.out.println("Email: " + customer.get_email());
                            System.out.println("Phone: " + customer.get_phone());
                            System.out.println();
                        }
                    }
                    break;

                case 6:
                    return; // Back to main menu

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void rentalManagementSystem(Scanner scanner, RentalService rentalService, CustomerService customerService, BookService bookService) {
        while (true) {
            System.out.println("\nRental Management");
            System.out.println("1. Rent a Book");
            System.out.println("2. Return a Book");
            System.out.println("3. View Borrowed Books by Customer");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    // Rent a Book
                    System.out.print("Enter Customer ID: ");
                    UUID customerId;
                    try {
                        customerId = UUID.fromString(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Customer ID format.");
                        break;
                    }

                    Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
                    if (optionalCustomer.isEmpty()) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    Customer customer = optionalCustomer.get();

                    System.out.print("Enter Book ID: ");
                    UUID bookId;
                    try {
                        bookId = UUID.fromString(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Book ID format.");
                        break;
                    }

                    Optional<Book> optionalBook = bookService.getBookById(bookId);
                    if (optionalBook.isEmpty()) {
                        System.out.println("Book not found.");
                        break;
                    }
                    Book book = optionalBook.get();

                    boolean rentSuccess = rentalService.borrowBook(customer, book);
                    if (rentSuccess) {
                        System.out.println("Book rented successfully.");
                    } else {
                        System.out.println("Failed to rent book.");
                    }
                    break;

                case 2:
                    // Return a Book
                    System.out.print("Enter Customer ID: ");
                    try {
                        customerId = UUID.fromString(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Customer ID format.");
                        break;
                    }

                    optionalCustomer = customerService.getCustomerById(customerId);
                    if (optionalCustomer.isEmpty()) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    customer = optionalCustomer.get();

                    System.out.print("Enter Book ID: ");
                    try {
                        bookId = UUID.fromString(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Book ID format.");
                        break;
                    }

                    optionalBook = bookService.getBookById(bookId);
                    if (optionalBook.isEmpty()) {
                        System.out.println("Book not found.");
                        break;
                    }
                    book = optionalBook.get();

                    boolean returnSuccess = rentalService.returnBook(customer, book);
                    if (returnSuccess) {
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Failed to return book.");
                    }
                    break;

                case 3:
                    // View Borrowed Books by Customer
                    System.out.print("Enter Customer ID: ");
                    try {
                        customerId = UUID.fromString(scanner.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid Customer ID format.");
                        break;
                    }

                    optionalCustomer = customerService.getCustomerById(customerId);
                    if (optionalCustomer.isEmpty()) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    customer = optionalCustomer.get();

                    System.out.println("Borrowed Books:");
                    if (customer.get_borrowedBooks().isEmpty()) {
                        System.out.println("No borrowed books found.");
                    } else {
                        customer.get_borrowedBooks().forEach(bookItem ->
                                System.out.println(bookItem.GetBookToString()));
                    }
                    break;

                case 4:
                    return; // Back to main menu

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
