import org.example.*;

import java.util.List;

public class WriteToDBMain {
    public static void main(String[] args) throws Exception {
        CRUD crud = new CRUD();
        crud.drop(Book.class);
        crud.drop(Author.class);
        crud.drop(Interpreter.class);
        crud.drop(Publisher.class);
        crud.drop(Reader.class);
        crud.create(Book.class);
        crud.create(Author.class);
        crud.create(Interpreter.class);
        crud.create(Publisher.class);
        crud.create(Reader.class);

        List<Book> books = Deserialize.deserialize();

        for (Book b:books) {
            crud.insert(b);

            crud.insert(b.getAuthor());

            for (Reader r: b.getReaders()) {
                crud.insert(r);
            }

            crud.insert(b.getPublisher());

            crud.insert(b.getInterpreters());

        }
    }


}
