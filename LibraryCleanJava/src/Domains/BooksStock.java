package Domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksStock {
    @JsonProperty("id")
    private UUID _id;
    @JsonProperty("bookId")
    private UUID _bookId;
    private Book _book;
    @JsonProperty("quantity")
    private int _quantity;


    public BooksStock(Book _book, int _quantity) {
        this._id = UUID.randomUUID();
        this._book = _book;
        this._quantity = _quantity;
    }
}
