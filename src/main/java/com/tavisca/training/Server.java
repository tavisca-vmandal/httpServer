package com.tavisca.training;

import  static com.tavisca.training.MyLogger.logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    private ServerSocket serverSocket = null;

    private Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started");
        }
        catch(IOException e) {
            logger.info(e.getMessage());
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
                logger.warning(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.handleRequest();
    }
}
