package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Book {
    private Long bookId;
    private String name;
    private Author author;
    private Interpreter interpreters;
    private Publisher publisher;
    private List<Reader> readers;

    public Book(Long bookId, String name) {
        this.bookId = bookId;
        this.name = name;
    }
}
