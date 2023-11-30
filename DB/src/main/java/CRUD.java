import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUD {
    private Mapper mapper = new Mapper();
    private DBConnection connection = new DBConnection();

    public CRUD() {
    }

    public String createQuaery(Class cls) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Field> primitiveFields = mapper.getByClass(cls);
        StringBuilder sb = new StringBuilder();
        String nl = "\n";
        sb.append(String.format("CREATE TABLE IF NOT EXISTS %s (", cls.getSimpleName())).append(nl);


        for(int i = 0; i < primitiveFields.size(); i++) {
            sb.append(getRow(
                    primitiveFields.get(i).getName(), primitiveFields.get(i).getType(), cls)
                    ).
                    append((i != primitiveFields.size()-1) ? "," : "").
                    append(nl);
        }
        sb.append(");").append(nl);
        System.out.println(sb.toString());
        System.out.println();
        return sb.toString();
    }

    public void create(Class cls) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SQLException {

        execute(createQuaery(cls));
    }

    public String dropQuaery(Class cls) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println( String.format("DROP TABLE IF EXISTS %s;", cls.getSimpleName()));
        System.out.println();
        return String.format("DROP TABLE IF EXISTS %s;", cls.getSimpleName());
    }

    public void drop(Class cls) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, SQLException {

        execute(dropQuaery(cls));
    }



    public String insertQuaery(Object object) throws Exception {
        List<Field> primitiveFields = mapper.getByClass(object.getClass());
        StringBuilder sb = new StringBuilder();
        String nl = "\n";
        sb.append(String.format("INSERT INTO %s VALUES (", object.getClass().getSimpleName())).append(nl);

        for(int i = 0; i < primitiveFields.size(); i++) {
            Object obj = getFieldValue(object, primitiveFields.get(i).getName());
            sb.append(
                    (obj.getClass().equals(String.class)) ? String.format("\'%s\'", obj.toString()) : obj.toString())
                    .append((i != primitiveFields.size()-1) ? "," : "")
                    .append(nl);
        }
        sb.append(");");
        System.out.println(sb.toString());
        System.out.println();
        return sb.toString();
    }
    public void insert(Object object) throws Exception {
        execute(insertQuaery(object));
    }



    public String findAllQuaery(Class cls){
        System.out.println(String.format("SELECT * FROM %s;", cls.getSimpleName()));
        System.out.println();
        return String.format("SELECT * FROM %s;", cls.getSimpleName());
    }

    public List<Object> findAll(Class cls) throws SQLException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        return mapper.createMapping(
                executeQuaery(
                        findAllQuaery(cls)
                ),
                cls
        );
    }



    public String findQuaery(Object object) throws Exception {
        StringBuilder sb = new StringBuilder();
        String nl = "\n";

        List<Field> notNulable = getNotNulable(object);
        sb.append(String.format("SELECT * FROM %s WHERE", object.getClass().getSimpleName())).append(nl);

        for (int i = 0; i < notNulable.size(); i++) {
            Object value = getFieldValue(object, notNulable.get(i).getName());
            sb.append
                    (notNulable.get(i).getName()+" = ").append
                    ((value.getClass().equals(String.class)) ? String.format("\'%s\'", value.toString()) : value.toString()).append
                    ((i != notNulable.size()-1) ? " and " : ";").append
                    (nl);
        }
        String.format(sb.toString());
        System.out.println();
        return sb.toString();
    }
    public List<Object> find(Object object) throws Exception {
        return mapper.createMapping(
                executeQuaery(
                        findQuaery(object)
                ),
                object.getClass()
        );
    }



    public String deleteQuaery(Object object) throws Exception {
        StringBuilder sb = new StringBuilder();
        String nl = "\n";

        List<Field> notNulable = getNotNulable(object);
        sb.append(String.format("DELETE FROM %s WHERE", object.getClass().getSimpleName())).append(nl);

        for (int i = 0; i < notNulable.size(); i++) {
            Object value = getFieldValue(object, notNulable.get(i).getName());
            sb.append
                    (notNulable.get(i).getName()+" = ").append
                    ((value.getClass().equals(String.class)) ? String.format("\'%s\'", value.toString()) : value.toString()).append
                    ((i != notNulable.size()-1) ? " and " : ";").append
                    (nl);
        }
        String.format(sb.toString());
        System.out.println();
        return sb.toString();
    }
    public void delete(Object object) throws Exception {
        execute(
                deleteQuaery(object)
        );
    }

    public String updateQuaery(Object lastObj, Object newObj) throws Exception {
        StringBuilder sb = new StringBuilder();
        String nl = "\n";

        sb.append(String.format("UPDATE %s SET", lastObj.getClass().getSimpleName())).append(nl);

        List<Field> newNotNulable = getNotNulable(newObj);

        for (int i = 0; i < newNotNulable.size(); i++) {
            Object value = getFieldValue(newObj, newNotNulable.get(i).getName());
            sb.append
                    (newNotNulable.get(i).getName()+" = ").append
                    ((value.getClass().equals(String.class)) ? String.format("\'%s\'", value.toString()) : value.toString()).append
                    ((i != newNotNulable.size()-1) ? "," : "").append
                    (nl);
        }
        sb.append("WHERE").append(nl);
        List<Field> lastNotNulable = getNotNulable(lastObj);

        for (int i = 0; i < lastNotNulable.size(); i++) {
            Object value = getFieldValue(lastObj, lastNotNulable.get(i).getName());
            sb.append
                    (lastNotNulable.get(i).getName()+" = ").append
                    ((value.getClass().equals(String.class)) ? String.format("\'%s\'", value.toString()) : value.toString()).append
                    ((i != lastNotNulable.size()-1) ? " and " : ";").append
                    (nl);
        }
        String.format(sb.toString());
        System.out.println();
        return sb.toString();
    }
    public void update(Object lastObj, Object newObj) throws Exception {
        executeUpdate(
                updateQuaery(lastObj, newObj)
        );
    }








































    private boolean isPK(String fieldName, Class cls){
        return (cls.getSimpleName()+"id").toLowerCase().equals(fieldName.toLowerCase());
    }

    private String getRow(String fieldName, Class fieldType, Class cls){
        String row = String.format("%s %s", fieldName, convertType(fieldType));
        if(isPK(fieldName, cls)){
            row += " PRIMARY KEY";
        }
        return row;
    }
    private String convertType(Class fieldType){
        if (fieldType.equals(Long.class))
            return "BIGINT";
        else if (fieldType.equals(String.class)) {
            return String.format("VARCHAR(%d)", 256);
        }
        return null;
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Method getter = clazz.getMethod("get" + capitalizeFirstLetter(fieldName));
        return getter.invoke(object);
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private List<Field> getNotNulable(Object obj) throws Exception {
        List<Field> notNulable = new ArrayList<>();

        for (Field f: mapper.getByClass(obj.getClass())) {
            if(getFieldValue(obj, f.getName()) != null){
                notNulable.add(f);
            }
        }
        return notNulable;
    }










    public void executeUpdate(String quaery) throws SQLException {
        connection.getConnection().createStatement().executeUpdate(quaery);
    }
    public void execute(String quaery) throws SQLException {
        System.out.println(quaery);
        connection.getConnection().createStatement().execute(quaery);
    }

    public ResultSet executeQuaery(String request) throws SQLException {
        return connection.getConnection().createStatement().executeQuery(request);
    }

    public Mapper getMapper() {
        return mapper;
    }
}
