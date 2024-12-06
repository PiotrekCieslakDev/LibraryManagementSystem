package Domains;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Customer {
    private UUID _id;
    private String _firstName;
    private String _lastName;
    private String _email;
    private String _phone;
    private List<Book> _boroowedBooks;

    public Customer(String _firstName, String _lastName, String _email, String _phone, List<Book> _boroowedBooks) {
        _id = UUID.randomUUID();
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._email = _email;
        this._phone = _phone;
        this._boroowedBooks = _boroowedBooks;
    }
}
