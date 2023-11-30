package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    private Long readerId;
    private Long bookId;
    private String name;
    private Long age;
    private String nativeLanguage;
}
