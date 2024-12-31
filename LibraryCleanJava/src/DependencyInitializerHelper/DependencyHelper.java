package DependencyInitializerHelper;

import DataAccessLayerJSON.BookJSON;
import DataAccessLayerJSON.BooksStockJSON;
import DataAccessLayerJSON.CustomerJSON;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DependencyHelper {
    public static ICustomerDAL customerDAL = new CustomerJSON();
    public static IBookDAL bookDAL = new BookJSON();
    public static IBooksStockDAL booksStockDAL = new BooksStockJSON();
}
