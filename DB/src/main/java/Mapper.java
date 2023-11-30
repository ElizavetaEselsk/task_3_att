
import org.example.*;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private List<Field> bookFealds;
    private List<Field> authorFealds;
    private List<Field> interpreterFealds;
    private List<Field> publisherFealds;
    private List<Field> readerFealds;

    public Mapper() {
        this.bookFealds = getPrimitiveFields(Book.class);
        this.authorFealds = getPrimitiveFields(Author.class);
        this.interpreterFealds = getPrimitiveFields(Interpreter.class);
        this.publisherFealds = getPrimitiveFields(Publisher.class);
        this.readerFealds = getPrimitiveFields(Reader.class);
    }

    public List<Object> createMapping(ResultSet resultSet, Class cls) throws SQLException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException {
        List<Object> books = new ArrayList<>();
        while (resultSet.next()){

            Class c = Class.forName(String.format("org.example.%s", (cls.getSimpleName())));;
            Object object = c.newInstance();
            for (Field f:getByClass(cls)) {
                setFieldValue(
                        object,
                        f.getName(),
                        resultSet.getObject(
                                f.getName()
                        )
                );
            }
            books.add(object);
        }
        return books;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private List<Field> getPrimitiveFields(Class cls){
        List<Field> fields = new ArrayList<>();
        Field[] fs = cls.getDeclaredFields();
        for(int i = 0; i < fs.length; i++) {
            if (fs[i].getType().equals(Long.class) || fs[i].getType().equals(String.class))
                fields.add(fs[i]);
        }
        return fields;
    }

    public List<Field> getBookFealds() {
        return bookFealds;
    }

    public List<Field> getAuthorFealds() {
        return authorFealds;
    }

    public List<Field> getInterpreterFealds() {
        return interpreterFealds;
    }

    public List<Field> getPublisherFealds() {
        return publisherFealds;
    }

    public List<Field> getReaderFealds() {
        return readerFealds;
    }

    public List<Field> getByClass(Class cls) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = this.getClass();
        Method getter = clazz.getMethod("get" + cls.getSimpleName()+"Fealds");
        return (List<Field>) getter.invoke(this);
    }
}
