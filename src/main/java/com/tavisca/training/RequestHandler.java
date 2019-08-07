package com.tavisca.training;

import java.io.*;
import java.net.Socket;

import static com.tavisca.training.MyLogger.logger;

class RequestHandler implements Runnable
{
    private Socket socket;

    RequestHandler(Socket socket) {
        this.socket=socket;
    }
    @Override
    public void run() {
        try(BufferedInputStream socketInputStream=new BufferedInputStream(socket.getInputStream())){
            String request=getRequest(socketInputStream);
            System.out.println(request);
            logger.info("Request: " +request);
            processRequest(request);
            socket.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
    private String getRequest(BufferedInputStream socketInputStream) throws IOException {
        byte[] buffer=new byte[socketInputStream.available()];
        socketInputStream.read(buffer);
        String request=new String(buffer);
        return request;
    }
    private void processRequest(String request) {
        try {
            RequestParser requestParser =new RequestParser();
            requestParser.parseRequest(request);
            ResponseHandler responseHandler =new ResponseHandler(socket,requestParser.getRequestedFile());
            responseHandler.sendResponse();
        } catch (IOException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}