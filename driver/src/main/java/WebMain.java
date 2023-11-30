public class WebMain {
    public static void main(String[] args) {
        CRUD crud = new CRUD();

        ServletsContainer servletsContainer = new ServletsContainer(crud);
        servletsContainer.start();
    }
}
