//необходимо реализовать монитор
package Conroller;

import View.MainWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer
{
    private final int PORT=8080;//не делать константой, сделать возможность выбора другого порта
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
                mainWindow.deliverMessage("\nClient accepted");
                SocketProcessor socketProcessor=new SocketProcessor(socket, mainWindow);
                socketProcessor.start();
            }
        }
        catch(IOException e)
        {
            mainWindow.deliverMessage("Server Connection error: "+e.getMessage());
        }
    }

    /*private class SocketProcessor extends Thread
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
            String contentType = "";
            String fileDate = "";

            if(!httpVersion.endsWith("1.1"))
            {
                contentType=getContentType(HTTP_VERSION_NOT_SUPPORTED_PAGE);
                fileDate=readFromFile(HTTP_VERSION_NOT_SUPPORTED_PAGE);
                answer(fileDate, contentType, ResponseCodes.HTTP_VERSION_NOT_SUPPORTED, ResponseCodes.getInformationAbout(ResponseCodes.HTTP_VERSION_NOT_SUPPORTED));
            }
            else
            {
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
                        if (command.equals("GET"))
                        {
                            if (documentAddress.endsWith("/"))
                            {
                                documentAddress = DEFAULT_PAGE;
                            }
                            else
                            {
                                documentAddress = documentAddress.substring(1, documentAddress.length());
                            }
                            if (!existFile(documentAddress))
                            {
                                contentType = getContentType(NOT_FOUND_PAGE);
                                fileDate = readFromFile(NOT_FOUND_PAGE);
                                answer(fileDate, contentType, ResponseCodes.NOT_FOUND, ResponseCodes.getInformationAbout(ResponseCodes.NOT_FOUND));
                            }
                            else
                            {
                                contentType = getContentType(documentAddress);
                                fileDate = readFromFile(documentAddress);
                                answer(fileDate, contentType, ResponseCodes.OK, ResponseCodes.getInformationAbout(ResponseCodes.OK));
                            }
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            mainWindow.deliverMessage("\nServer error: "+e.getMessage());
        }
    }

    private boolean existFile(String documentAddress)
    {
        if(new File(documentAddress).exists())
            return true;
        else
            return false;
    }

    private String getContentType(String documentAddress) {
        if (documentAddress.endsWith(".html"))
            return "text/html";
        else
            return "text/plain";
    }

    private String readFromFile(String documentAddress)
    {
        FileReader fr=null;
        Scanner scanner=null;
        String string="";
        try
        {
            fr=new FileReader(documentAddress);
            scanner=new Scanner(fr);
            while(scanner.hasNextLine())
            {
                string+=scanner.nextLine();
            }
        }
        catch(IOException e)
        {
            //string=Integer.toString(ResponseCodes.NOT_FOUND);
            mainWindow.deliverMessage("\nReading from file error: "+e.getMessage());
        }
        finally
        {
            if(fr!=null)
                try
                {
                    fr.close();
                }
                catch(IOException e)
                {
                    mainWindow.deliverMessage("\nClose FileInputStream error: "+e.getMessage());//требуются доработки
                }
        }
        return string;
    }

    private void answer(String fileDate,
                        String contentType,
                        int responseCode,
                        String infoAboutCode)
    {
        String response = "\nHTTP/1.1 "+responseCode+" "
                +infoAboutCode+"\r\n" +
                "Date: "+new Date()+"\r\n"+
                "Server: HTTPServer by Ruslan\r\n" +
                "Content-Type: "+contentType+"\r\n" +
                "Content-Length: " + fileDate.length() + "\r\n" +
                "\r\n\r\n";
        try
        {
            out.write(response+fileDate);
            out.flush();
        }
        catch(IOException e)
        {
            mainWindow.deliverMessage("Error: "+e.getMessage());
        }
        mainWindow.deliverMessage(response);
    }

    private void answerBadRequest()//требует доработки
    {
        String response="\nHTTP/1.1 "+ResponseCodes.BAD_REQUEST+
                " "+ResponseCodes.getInformationAbout(ResponseCodes.BAD_REQUEST)+
                "\nDate: "+new Date()+
                "\nServer: HTTP Server/1.1"+
                "\nLast-modified: "+
                "\nContent-type: "+
                "\nContent-length: ";
        mainWindow.deliverMessage(response);
    }

    private void answerNotImplemented()//требуются доработки
    {
        String response="\nHTTP/1.1 "+ResponseCodes.NOT_IMPLEMENTED+
                " "+ResponseCodes.getInformationAbout(ResponseCodes.NOT_IMPLEMENTED)+
                "\nDate: "+new Date()+
                "\nServer: HTTP Server/1.1"+
                "\nLast-modified: "+
                "\nContent-type: "+
                "\nContent-length: ";
        mainWindow.deliverMessage(response);
    }
}*/
}