package com.tavisca.training;
import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket = null;

    private Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            MyLogger.log("Server started");
        }
        catch(IOException e) {
           MyLogger.log(e.getMessage());
        }
    }
    private void handleRequest() {
        while(true){
            try {
                Socket  socket = serverSocket.accept();
                RequestHandler requestHandler=new RequestHandler(socket);
                Thread thread=new Thread(requestHandler);
                thread.start();
            } catch (IOException e) {
                MyLogger.log(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.handleRequest();
    }
}
