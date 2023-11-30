import org.example.*;

import java.io.IOException;
import java.util.List;



public class ReadMain {
    public static void main(String[] args) throws IOException {
        List<Book> books = Deserialize.deserialize();

        for (Book b : books) {
            System.out.println(b.getBookId());
            System.out.println(b.getName());
            System.out.println(b.getAuthor());
            System.out.println(b.getInterpreters());
            System.out.println(b.getPublisher());
            System.out.println(b.getReaders());
            System.out.println("____________________________");
        }

    }

}