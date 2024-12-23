package Domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @JsonProperty("id") // Maps the "id" field in JSON to this property
    private UUID _id;

    @JsonProperty("firstName") // Maps "firstName" in JSON
    private String _firstName;

    @JsonProperty("lastName") // Maps "lastName" in JSON
    private String _lastName;

    @JsonProperty("email") // Maps "email" in JSON
    private String _email;

    @JsonProperty("phone") // Maps "phone" in JSON
    private String _phone;

    @JsonProperty("borrowedBooks") // Maps "borrowedBooks" in JSON
    private List<Book> _borrowedBooks;

    public Customer(String firstName, String lastName, String email, String phone, List<Book> borrowedBooks) {
        _id = UUID.randomUUID();
        this._firstName = firstName;
        this._lastName = lastName;
        this._email = email;
        this._phone = phone;
        this._borrowedBooks = borrowedBooks;
    }
}
