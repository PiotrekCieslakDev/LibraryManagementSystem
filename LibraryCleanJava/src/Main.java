import DataAccessLayer.*;
import DataAccessLayerJSON.*;
import InitializeHelper.*;
import Service.*;
import Domains.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the DALs
        BookDAL bookDAL = new BookDAL();
        BooksStockDAL booksStockDAL = new BooksStockDAL();
        CustomerDAL customerDAL = new CustomerDAL();

        // Initialize the services
        BookService bookService = new BookService(bookDAL);
        BooksStockService booksStockService = new BooksStockService(booksStockDAL);
        CustomerService customerService = new CustomerService(customerDAL);
        StockCalculatingService stockCalculatingService = new StockCalculatingService(booksStockDAL, customerDAL);

        // Initialize books
        new BooksInitializer(bookDAL);

        // Initialize stock
        new BooksStockInitializer(bookDAL, booksStockDAL);

        // Initialize customers
        //new CustomersInitializer(customerDAL);

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Stocks");
            System.out.println("3. Manage Customers");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

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

            switch (option) {
                case 1:
                    // Add Book
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publisher: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    bookService.createBook(title, author, publisher, year);
                    System.out.println("Book added successfully.");
                    break;

                case 2:
                    // View All Books
                    System.out.println("Books in the Library:");
                    bookService.getAllBooks().forEach(book -> System.out.println(book.GetBookToString()));
                    break;

                case 3:
                    // Update Book
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

                        bookService.updateBook(bookIdToUpdate, newTitle, newAuthor, newPublisher, newYear);
                        System.out.println("Book updated successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 4:
                    // Delete Book
                    System.out.print("Enter the ID of the book to delete: ");
                    UUID bookIdToDelete = UUID.fromString(scanner.nextLine());
                    if (bookService.deleteBook(bookIdToDelete)) {
                        System.out.println("Book deleted successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                case 5:
                    // Search Books
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
            System.out.println("6. Back");
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
                    System.out.println("Customer added successfully.");
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

}
