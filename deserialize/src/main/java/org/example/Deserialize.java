package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Deserialize {

    public static List<Book>  deserialize() throws IOException {
        return new ObjectMapper().readValue(new FileInputStream("books.json"),
                new TypeReference<List<Book>>() {});
    }

}
