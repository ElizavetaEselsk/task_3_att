import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.*;

public class ServletsContainer {


    private static final int PORT = 8080;
    private CRUD crud;

    public ServletsContainer(CRUD crud) {
        this.crud = crud;
    }

    public void start(){
        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder( new Servlet( Author.class,crud) ),"/author");
        context.addServlet(new ServletHolder( new Servlet( Book.class,crud) ),"/book");
        context.addServlet(new ServletHolder( new Servlet( Interpreter.class,crud) ),"/interpreter");
        context.addServlet(new ServletHolder( new Servlet( Publisher.class,crud) ),"/publisher");
        context.addServlet(new ServletHolder( new Servlet( Reader.class,crud) ),"/reader");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });
        server.setHandler(handlers);

        try {
            server.start();
            System.out.println("Listening port : " + PORT );

            server.join();
        } catch (Exception e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }


}