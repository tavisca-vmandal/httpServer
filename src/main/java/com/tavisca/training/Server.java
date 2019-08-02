package com.tavisca.training;

import java.net.*;
import java.io.*;

public class Server
{
    private static ServerSocket serverSocket = null;

    public Server(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch(IOException i)
        {
            System.out.println(i);

        }
    }
    private void handleClient(ServerSocket serverSocket) throws IOException {

        while(true)
        {
            System.out.println("Waiting for a client ...");
            Socket socket=serverSocket.accept();
            MultipleClientHandler multipleClientHandler=new MultipleClientHandler(socket);
            Thread thread=new Thread(multipleClientHandler);
            thread.start();

        }
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
        System.out.println("Server started");

        try {
            server.handleClient(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
