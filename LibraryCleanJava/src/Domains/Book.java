package Domains;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Book {
    private UUID _id;
    private String _title;
    private String _author;
    private String _publisher;
    private int _year;

    public Book(String title, String author, String publisher, int year) {
        _id = UUID.randomUUID();
        _title = title;
        _author = author;
        this._publisher = publisher;
        this._year = year;
    }

    public String GetBookToString() {
        return "\"" + _title + "\" by " + _author + " || Published by " + _publisher + " in " + _year + " || ID: " + _id;
    }

}
