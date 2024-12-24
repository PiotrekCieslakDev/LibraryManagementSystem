package Interfaces;

import Domains.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Interface for Book database access, all methods are inherently abstract as it is an interface
public interface IBookDAL {
    //Retrievers
    //Retriever for getting one specific book by its ID
    public Optional<Book> getBookById(UUID id);
    //Retriever for getting all books in the database
    public List<Book> getAllBooks();
    //TODO! get book/s by any parameter abstract method

    //Abstract create book method
    public boolean createBook(Book book);

    //Abstract update book method
    public boolean updateBook(Book book);

    //Abstract delete book method
    public boolean deleteBook(UUID id);
}
