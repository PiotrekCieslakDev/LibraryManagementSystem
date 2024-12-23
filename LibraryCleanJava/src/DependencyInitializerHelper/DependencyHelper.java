package DependencyInitializerHelper;

import DataAccessLayer.BookDAL;
import DataAccessLayer.BooksStockDAL;
import DataAccessLayer.CustomerDAL;
import DataAccessLayerJSON.CustomerJSON;
import Interfaces.IBookDAL;
import Interfaces.IBooksStockDAL;
import Interfaces.ICustomerDAL;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DependencyHelper {
    public static ICustomerDAL customerDAL = new CustomerJSON();
    public static IBookDAL bookDAL = new BookDAL();
    public static IBooksStockDAL booksStockDAL = new BooksStockDAL();
}
