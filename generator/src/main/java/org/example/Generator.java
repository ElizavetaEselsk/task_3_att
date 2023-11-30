package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public static List<Book> randomGenerations(int countObject) {

        Random rd = new Random();
        List<Book> books = new ArrayList<Book>();

        for (int i = 0; i < countObject; i++) {

            long idB = (i+1)*1000;
            long idA = (i+1)*10000;
            long idI = (i+1)*100000;
            long idP = (i+1)*1000000;


            List<Reader> readers = new ArrayList<Reader>();

            for (int j = 0; j < 3; j++) {
                long idR = idB+j+1;
                Reader reader = new Reader(
                        idR,
                        idB,
                        "Name_" + rd.nextInt(),
                        (long) (rd.nextInt(60) + 20),
                        Language.values()[rd.nextInt(Language.values().length)].name());
                readers.add(reader);
            }

            Book book = new Book(
                    idB,
                    "Name_" + rd.nextInt(),
                    new Author(
                            idA,
                            idB,
                            "Name_" + rd.nextInt(),
                            Language.values()[rd.nextInt(Language.values().length)].name(),
                            (long) (rd.nextInt(60) + 20)
                    ),
                    new Interpreter(
                            idB,
                            idI,
                            "Name_" + rd.nextInt(),
                            (long) (rd.nextInt(60) + 20),
                            Language.values()[rd.nextInt(Language.values().length)].name()
                    ),
                    new Publisher(
                            idP,
                            idB,
                            PublisherName.values()[rd.nextInt(PublisherName.values().length)].name()
                    ),
                    readers
            );

            books.add(book);
        }

        return books;
    }
}
