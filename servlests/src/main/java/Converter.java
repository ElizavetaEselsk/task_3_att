import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Converter {

    public static Object fromMap(CRUD crud, Map<String, Object> values, Class c) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        Object obj = c.newInstance();

        for (Field f:crud.getMapper().getByClass(c)) {
            Object val = values.getOrDefault(f.getName(), null);
            if(val == null)
                continue;
            setVal(crud, f, obj, val.toString());
        }

        return obj;
    }

    public static Object fromRequest(CRUD crud, HttpServletRequest request, Class c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        return fromRequest(crud, request,c,"");
    }
    public static Object fromRequest(CRUD crud, HttpServletRequest request, Class c, String condition) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        Object obj = c.newInstance();

        int count = 0;
        for (Field f:crud.getMapper().getByClass(c)) {
            String val = request.getParameter(condition+f.getName());
            if(val == null)
                continue;
            setVal(crud, f, obj, val);
            count++;
        }

        return count == 0 ? null : obj;
    }
    private static void setVal(CRUD crud, Field f, Object obj, String val) throws NoSuchFieldException, IllegalAccessException {
        if(f.getType().equals(String.class)) {
            crud.getMapper().setFieldValue(obj, f.getName(), val);
        } else if (f.getType().equals(Long.class) || f.getType().equals(long.class)) {
            crud.getMapper().setFieldValue(obj, f.getName(), Long.parseLong(val));
        } else if (f.getType().equals(Integer.class) || f.getType().equals(int.class)) {
            crud.getMapper().setFieldValue(obj, f.getName(), Integer.parseInt(val));
        }
    }

}
