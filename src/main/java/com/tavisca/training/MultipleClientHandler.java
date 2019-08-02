package com.tavisca.training;

import java.io.*;
import java.net.Socket;

class MultipleClientHandler implements Runnable
{

    private Socket socket;
    private   BufferedInputStream bufferedInputStream;

    MultipleClientHandler(Socket socket)
    {
        this.socket=socket;
    }

    @Override
    public void run() {
        
        try {

            System.out.println("Client accepted");
            String socketInputStream = printSocketInputStream();
            Response response=new Response(socket,socketInputStream);
            response.sendResponse();

            socket.close();
            bufferedInputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String printSocketInputStream() throws IOException {

        bufferedInputStream = new BufferedInputStream(socket.getInputStream());

        byte[] buffer=new byte[bufferedInputStream.available()];
        bufferedInputStream.read(buffer);
        String content=new String(buffer);

        System.out.println(content);
        return content;
    }

}