package InitializeHelper;

import Domains.Book;
import Interfaces.IBookDAL;

//Class for initializing hard-coded data in case of not using real persistence DB
public class BooksInitializer {
    //Access for Database
    private final IBookDAL _bookDAL;

    public BooksInitializer(IBookDAL bookDAL) {
        _bookDAL = bookDAL;
        initializeBooks();
    }

    //Initializing the books
    private void initializeBooks() {
        _bookDAL.createBook(new Book("To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", 1960));
        _bookDAL.createBook(new Book("1984", "George Orwell", "Secker & Warburg", 1949));
        _bookDAL.createBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Charles Scribner's Sons", 1925));
        _bookDAL.createBook(new Book("The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", 1951));
        _bookDAL.createBook(new Book("Pride and Prejudice", "Jane Austen", "T. Egerton", 1813));
        _bookDAL.createBook(new Book("The Hobbit", "J.R.R. Tolkien", "George Allen & Unwin", 1937));
        _bookDAL.createBook(new Book("Moby Dick", "Herman Melville", "Harper & Brothers", 1851));
        _bookDAL.createBook(new Book("War and Peace", "Leo Tolstoy", "The Russian Messenger", 1869));
        _bookDAL.createBook(new Book("Crime and Punishment", "Fyodor Dostoevsky", "The Russian Messenger", 1866));
        _bookDAL.createBook(new Book("Brave New World", "Aldous Huxley", "Chatto & Windus", 1932));
    }
}

