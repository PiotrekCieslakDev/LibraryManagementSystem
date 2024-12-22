package DomainHelpers;

import Domains.BooksStock;
import lombok.Data;

@Data
public class BooksStockWithAvailability extends BooksStock {

    private int _availableQuantity;


    // Constructor that creates an object based on an existing BooksStock object
    public BooksStockWithAvailability(BooksStock booksStock, int availableQuantity) {
        super(booksStock.get_id(), booksStock.get_book(), booksStock.get_quantity()); // Pass values to the parent constructor
        _availableQuantity = availableQuantity;
    }
}
