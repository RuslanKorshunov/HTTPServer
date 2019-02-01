package Conroller;

import View.MainWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class HTTPServer
{
    private final int PORT=8080;
    private MainWindow mainWindow;
    private ServerSocket serverSocket;
    private Socket socket;

    public HTTPServer(MainWindow mainWindow)
    {
        this.mainWindow=mainWindow;
        try
        {
            serverSocket = new ServerSocket(PORT);
            mainWindow.deliverMessage("Server started\nPort: "+PORT+"\n...");
            while (true)
            {
                socket=serverSocket.accept();
                mainWindow.deliverMessage("\nCleint accepted");
                SocketProcessor socketProcessor=new SocketProcessor(socket);
                socketProcessor.start();
            }
        }
        catch(IOException e)
        {
            mainWindow.deliverMessage("Server Connection error: "+e.getMessage());
        }
    }

    private class SocketProcessor extends Thread
    {
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;

        SocketProcessor(Socket s)
        {
            socket=s;
        }

        @Override
        public void run()
        {
            try
            {
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String request=in.readLine();
                mainWindow.deliverMessage("\n"+request);

                StringTokenizer parse = new StringTokenizer(request);
                String command=parse.nextToken();
                String documentAddress=parse.nextToken();
                String httpVersion=parse.nextToken();
                System.out.println(command+2);

                if(!command.equals(command.toUpperCase()))
                {
                    answerBadRequest();
                }
                else
                {
                    if(!command.equals("GET")&&!command.equals("POST")&&!command.equals("HEAD"))
                    {
                        answerNotImplemented();
                    }
                    else
                    {

                    }
                }
            }
            catch(IOException e)
            {
                mainWindow.deliverMessage("Server error: "+e.getMessage());
            }
        }

        private void answerBadRequest()//требует доработки
        {
            String message="HTTP/1.1 "+ResponseCodes.BAD_REQUEST+
                    " "+ResponseCodes.getInformationAbout(ResponseCodes.BAD_REQUEST)+
                    "\nDate: "+new Date()+
                    "\nServer: HTTP Server/1.1"+
                    "\nLast-modified: "+
                    "\nContent-type: "+
                    "\nContent-length: ";
            mainWindow.deliverMessage(message);
        }

        private void answerNotImplemented()
        {
            String message="HTTP/1.1 "+ResponseCodes.NOT_IMPLEMENTED+
                    " "+ResponseCodes.getInformationAbout(ResponseCodes.NOT_IMPLEMENTED)+
                    "\nDate: "+new Date()+
                    "\nServer: HTTP Server/1.1"+
                    "\nLast-modified: "+
                    "\nContent-type: "+
                    "\nContent-length: ";
            mainWindow.deliverMessage(message);
        }
    }
}