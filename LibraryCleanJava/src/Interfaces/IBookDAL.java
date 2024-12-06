package Interfaces;

import Domains.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBookDAL {
    //Retrievers
    public Optional<Book> getBookById(UUID id);
    public List<Book> getAllBooks();
    //TODO! get book/s by any parameter abstract method

    //Create
    public boolean createBook(Book book);

    //Update
    public boolean updateBook(Book book);

    //Delete
    public boolean deleteBook(UUID id);
}
