//необходимо реализовать монитор
package Conroller;

import View.MainWindow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HTTPServer
{
    private final File WEB_ROOT = new File(".");
    private final int PORT=8080;//не делать константой, сделать возможность выбора другого порта
    private MainWindow mainWindow;
    private ServerSocket serverSocket;
    private Socket socket;

    private final String DEFAULT_PAGE="files/main_page.html";

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
                        if(command.equals("GET"))
                        {
                            if(documentAddress.endsWith("/"))
                            {
                                documentAddress = DEFAULT_PAGE;
                            }
                            //System.out.println("2"+documentAddress+"2");
                            //String newDocumentAddress=DEFAULT_PAGE;
                            File file=new File(documentAddress);
                            //int fileLength=(int)file.length();
                            String contentType=getContentType(documentAddress);
                            String fileDate=readFromFile(documentAddress);
                            //char[]fileDate=readFromFile(documentAddress, fileLength);
                            answerOk(fileDate, contentType);
                        }
                    }
                }
            }
            catch(IOException e)
            {
                mainWindow.deliverMessage("\nServer error: "+e.getMessage());
            }
        }

        private String getContentType(String documentAddress) {
            if (documentAddress.endsWith(".htm")  ||  documentAddress.endsWith(".html"))
                return "text/html";
            else
                return "text/plain";
        }

        private String readFromFile(String documentAddress)
        {
            //char[] fileDate=new char[fileLength];
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
                System.out.println(string);
                /*int ch;
                int index=0;
                while((ch=fr.read())!=-1)
                {
                    if(index<fileLength)
                    {
                        fileDate[index]=(char)ch;
                        //System.out.println(fileDate[index]);
                    }
                }*/
            }
            catch(IOException e)
            {
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
                        mainWindow.deliverMessage("\nClose FileInputStream error: "+e.getMessage());
                    }
            }
            return string;
        }

        private void answerOk(String fileDate, String contentType)
        {
            String response = "\nHTTP/1.1 "+ResponseCodes.OK+" "
                    +ResponseCodes.getInformationAbout(ResponseCodes.OK)+"\r\n" +
                    "Date: "+new Date()+"\r\n"+
                    "Server: HTTPServer by Ruslan\r\n" +
                    "Content-Type: "+contentType+"\r\n" +
                    "Content-Length: " + fileDate.length() + "\r\n" +
                    "\r\n\r\n";
                    //"Connection: close\r\n\r\n";
            //response+=fileDate;
            /*String message="\nHTTP/1.1 "+ResponseCodes.OK+
                    " "+ResponseCodes.getInformationAbout(ResponseCodes.OK)+
                    "\nDate: "+new Date()+
                    "\nServer: HTTP Server/1.1"+
                    "\nLast-modified: "+new Date()+
                    "\nContent-type: "+contentType+
                    "\nContent-length: "+fileDate.length();*/
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
            String message="\nHTTP/1.1 "+ResponseCodes.BAD_REQUEST+
                    " "+ResponseCodes.getInformationAbout(ResponseCodes.BAD_REQUEST)+
                    "\nDate: "+new Date()+
                    "\nServer: HTTP Server/1.1"+
                    "\nLast-modified: "+
                    "\nContent-type: "+
                    "\nContent-length: ";
            mainWindow.deliverMessage(message);
        }

        private void answerNotImplemented()//требуются доработки
        {
            String message="\nHTTP/1.1 "+ResponseCodes.NOT_IMPLEMENTED+
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