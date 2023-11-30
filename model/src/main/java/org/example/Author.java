package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private Long authorId;
    private Long bookId;

    private String name;
    private String nativeLanguage;
    private Long age;


}
