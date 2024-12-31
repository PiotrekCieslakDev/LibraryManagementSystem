package DataAccessLayerJSON;

import Domains.Customer;
import Domains.Book;
import Domains.BooksStock;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
}

