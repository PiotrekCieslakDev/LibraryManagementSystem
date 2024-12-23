package Domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @JsonProperty("id") // Maps "id" in JSON to this field
    private UUID _id;

    @JsonProperty("title") // Maps "title" in JSON to this field
    private String _title;

    @JsonProperty("author") // Maps "author" in JSON to this field
    private String _author;

    @JsonProperty("publisher") // Maps "publisher" in JSON to this field
    private String _publisher;

    @JsonProperty("year") // Maps "year" in JSON to this field
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
