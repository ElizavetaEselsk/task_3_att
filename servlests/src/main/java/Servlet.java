import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    private Class aClass;
    private CRUD crud;

    public Servlet(Class aClass, CRUD crud) {
        this.aClass = aClass;
        this.crud = crud;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {


        List<Object> objects = new ArrayList<>();

        Object o = Converter.fromRequest(crud, request, aClass);
        if(o == null)
            objects = crud.findAll(aClass);
        else
            objects = crud.find(o);

        String ans = CreateAnswer.getAns(objects);
        if(ans.equals(""))
            ans = "Not found";

        response.getWriter().println(ans);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            crud.insert(Converter.fromRequest(crud, request, aClass));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.getWriter().println("POSTED");
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            crud.delete(Converter.fromRequest(crud, request, aClass));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.getWriter().println("DELETED");
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            crud.update(Converter.fromRequest(crud, request, aClass), Converter.fromRequest(crud, request, aClass, "CONDITION"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.getWriter().println("PUTTED");
    }

}