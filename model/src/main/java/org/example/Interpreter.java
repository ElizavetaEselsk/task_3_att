package org.example;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interpreter {
    private Long interpreterId;
    private Long bookId;

    private String name;
    private Long age;
    private String workingLanguage;


}
