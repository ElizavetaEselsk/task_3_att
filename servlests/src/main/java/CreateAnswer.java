import java.util.List;

public class CreateAnswer {
    public static String getAns(List<Object> objects){
        String s = "";

        for (Object o:objects) {
            s+=o.toString()+"\n";
        }
        return s;
    }
}
