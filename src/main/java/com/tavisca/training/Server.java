package com.tavisca.training;

import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server
{

    private ServerSocket server = null;

    public Server(int port)
    {

        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");


            while(true)
            {
                Socket socket=server.accept();

                MultipleClient multipleClient=new MultipleClient(socket);
                Thread thread=new Thread(multipleClient);
                thread.start();
                thread.join();

            }

        }
        catch(IOException i)
        {
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}

class MultipleClient implements Runnable
{

    private Socket socket = null;
    private BufferedInputStream in	 = null;


    MultipleClient(Socket socket)
    {
        this.socket=socket;
    }
    @Override
    public void run() {
        try {

            System.out.println(" New Client accepted");
            in = new BufferedInputStream(socket.getInputStream());
            byte[] buffer=new byte[in.available()];
            in.read(buffer);
            String content=new String(buffer);
            Pattern pattern=Pattern.compile("(.*)\\s\\/(.*)(HTTP\\/1\\.1)");
            Matcher matcher=pattern.matcher(content);

            System.out.println(content);

            System.out.println("Closing connection");

            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            out.print("HTTP/1.1 200 \r\n"); // Version & status code
            out.print("Server: My Java HTTP Server : 1.0");
            out.print("Content-Type: html\r\n"); // The type of data
            out.print(" Content-length: 256");
            out.print("Connection: close\r\n"); // Will close stream
            out.print("\r\n"); // End of headers


            if(matcher.find())
            {
                String fileName=matcher.group(2);

                FileReader fileReader=new FileReader(fileName);
                BufferedReader bufferedReader=new BufferedReader(fileReader);

                String thisLine="";

                while((thisLine=bufferedReader.readLine())!=null)
                    out.print(thisLine);
            }


            out.close();
            socket.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}