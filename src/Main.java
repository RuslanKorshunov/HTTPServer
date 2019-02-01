import Conroller.HTTPServer;
import View.MainWindow;

public class Main
{
    public static void main(String[]args) throws Throwable
    {
        MainWindow mainWindow=new MainWindow();
        HTTPServer httpServer=new HTTPServer(mainWindow);
    }
}
