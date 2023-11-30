import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Matcher {

    private CRUD crud;
    private Scanner scanner;

    public Matcher() {
        this.crud = new CRUD();
        scanner = new Scanner(System.in);
    }

    private static final Map<String, String> commands = Map.of(
            "findAll class_name", "find all values",
            "find class_name", "find values by criteria",
            "insert class_name", "insert value",
            "delete class_name", "delete value",
            "update class_name", " update value",
            "exit", "close console",
            "help", "command list"
    );


    public void help(){
        for (String key:commands.keySet()) {
            System.out.println(key +" -- " + commands.get(key));
        }
    }

    public void read() throws Exception {
        help();
        System.out.println("_________________________________");

        while (true) {
            String command = scanner.nextLine().trim();
            if (command.equals("help"))
                help();
            else if (command.equals("exit")) {
                return;
            }else{
                matchCommand(command);
                System.out.println();
            }
        }

    }
    public void matchCommand(String command) throws Exception {
        String[] criterias = command.split("\\s+");
        if(!List.of("book", "author", "interpreter", "publisher", "reader").contains(criterias[1].toLowerCase())){
            System.out.println(String.format("class with name <%s> not found", criterias[1]));
            return;
        }
        Class c = Class.forName(String.format("org.example.%s", capitalizeFirstLetter(criterias[1])));;
        Object object = c.newInstance();
        if(criterias[0].toLowerCase().trim().equals("insert")) {
            object = match(object);
            System.out.println(object);
            System.out.println();
            crud.insert(object);
        } else if (criterias[0].toLowerCase().trim().equals("findall")) {
            System.out.println(crud.findAll(c));
            System.out.println();
        } else if (criterias[0].toLowerCase().trim().equals("find")) {
            object = match(object);
            System.out.println(object);
            System.out.println();
            System.out.println(crud.find(object));
        } else if (criterias[0].toLowerCase().trim().equals("delete")) {
            object = match(object);
            System.out.println(object);
            System.out.println();
            crud.delete(object);
        } else if (criterias[0].toLowerCase().trim().equals("update")) {
            System.out.println("Old values:");
            object = match(object);
            System.out.println(object);
            System.out.println();

            Object newValues = c.newInstance();
            System.out.println("New values:");
            newValues = match(newValues);
            System.out.println(newValues);
            System.out.println();
            crud.update(object, newValues);



        } else {
            System.out.println(String.format("command <%s> not found", criterias[0]));
        }
    }

    public Object match(Object obj) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

        for (Field f:crud.getMapper().getByClass(obj.getClass())) {
            System.out.print(f.getName()+": ");
            String val = scanner.nextLine();
            if(val.equals(""))
                continue;
            if(f.getType().equals(String.class)){
                crud.getMapper().setFieldValue(obj, f.getName(), val);
            } else if (f.getType().equals(Long.class)) {
                crud.getMapper().setFieldValue(obj, f.getName(), Long.parseLong(val));
            }
        }
        return obj;
    }
    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
