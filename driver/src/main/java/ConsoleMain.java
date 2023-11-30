import org.example.*;



public class ConsoleMain {
    public static void main(String[] args) throws Exception {

        System.out.println(Book.class);

        CRUD crud = new CRUD();
        crud.create(Book.class);
        crud.create(Author.class);
        crud.create(Interpreter.class);
        crud.create(Publisher.class);
        crud.create(Reader.class);

        Matcher matcher = new Matcher();
        matcher.read();



    }
}
