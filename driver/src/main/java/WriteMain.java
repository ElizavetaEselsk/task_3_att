import org.example.*;

import java.io.IOException;

public class WriteMain {
    public static void main(String[] args) throws IOException {

        Serializer.serializer(Generator.randomGenerations(5));

    }

}