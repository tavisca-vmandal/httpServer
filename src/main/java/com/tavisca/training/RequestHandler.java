package com.tavisca.training;

import java.io.*;
import java.net.Socket;

class MultipleClientHandler implements Runnable
{

    private Socket socket;
    private   BufferedInputStream bufferedInputStream;
    MyLogger myLogger=new MyLogger();

    MultipleClientHandler(Socket socket)
    {
        this.socket=socket;
    }

    @Override
    public void run() {
        
        try{

            myLogger.log("Client accepted");

            processRequest();
            socket.close();
            bufferedInputStream.close();

        } catch (IOException e) {
            myLogger.log(e.getMessage());
            e.printStackTrace();
        }

    }

    private void processRequest()
    {

        try {
            String socketInputStream = printSocketInputStream();

            UrlParser urlParser=new UrlParser();
            String requestedFile=urlParser.parse(socketInputStream);

            Response response=new Response(socket,requestedFile);
            response.sendResponse();

        } catch (IOException e) {
            myLogger.log(e.getMessage());
            e.printStackTrace();
        }

    }

    private String printSocketInputStream() throws IOException {

        bufferedInputStream = new BufferedInputStream(socket.getInputStream());

        byte[] buffer=new byte[bufferedInputStream.available()];
        bufferedInputStream.read(buffer);
        String content=new String(buffer);
        System.out.println(content);
        myLogger.log("Request: " +content);
        return content;
    }

}