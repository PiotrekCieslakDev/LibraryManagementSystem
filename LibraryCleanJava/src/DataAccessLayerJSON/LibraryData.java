package DataAccessLayerJSON;

import Domains.Customer;
import Domains.Book;
import Domains.BooksStock;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any unknown properties
public class LibraryData {

    @JsonProperty("books")
    private List<Book> books;

    @JsonProperty("customers")
    private List<Customer> customers;

    @JsonProperty("stock")
    private List<BooksStock> stock;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<BooksStock> getStock() { // Adjusted getter
        return stock;
    }

    public void setStock(List<BooksStock> stock) { // Adjusted setter
        this.stock = stock;
    }

}

