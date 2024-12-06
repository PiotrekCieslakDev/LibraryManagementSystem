package Domains;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BooksStock {
    private UUID _id;
    private Book _book;
    private int _quantity;


    public BooksStock(Book _book, int _quantity) {
        this._id = UUID.randomUUID();
        this._book = _book;
        this._quantity = _quantity;
    }
}
