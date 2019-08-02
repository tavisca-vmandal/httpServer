package com.tavisca.training;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response{

    private BufferedOutputStream socketOutputStream;
    private int contentSize;
    private String socketInputStream;


    public Response(Socket socket, String socketInputStream) throws IOException {

        socketOutputStream =new BufferedOutputStream(socket.getOutputStream());
        this.socketInputStream=socketInputStream;


    }
    public void sendResponse() throws IOException {
        String fileName=parseURL(socketInputStream);

        try {

            fileName=indexPageFileAssignment(fileName);

            respondSuccessPage(fileName);

        } catch (FileNotFoundException e) {
            fileName="FileNotFound.html";
            respondErrorPage(fileName);
        }
        finally {
            System.out.println("Closing connection");
            socketOutputStream.close();
        }
    }
    private void respondErrorPage(String fileName) throws IOException {


        httpHeader(404,"html",contentSize);
        FileInputStream fileInputStream=new FileInputStream(fileName);
        contentSize=fileInputStream.available();
        byte[] buffer=new byte[contentSize];
        fileInputStream.read(buffer);
        socketOutputStream.write(buffer);

    }

    private void respondSuccessPage(String fileName) throws IOException {


        FileInputStream fileInputStream=new FileInputStream(fileName);
        contentSize=fileInputStream.available();
        byte[] buffer=new byte[contentSize];
        String[] extractFileExtension=fileName.split("\\.");
        String extension=extractFileExtension[1];
        String contentType="html";
        if(extension.equals("png"))
            contentType="image/png";

        fileInputStream.read(buffer);

        httpHeader(202,contentType,contentSize);

        socketOutputStream.write(buffer);

    }

    private String indexPageFileAssignment(String fileName) {

        if(fileName.trim().isEmpty())
        {
            fileName="index.html";
        }
        return fileName;
    }

    private String parseURL(String content)
    {
        String fileName="";
        Pattern pattern=Pattern.compile("(.*)\\s\\/(.*)(HTTP\\/1\\.1)");
        Matcher matcher=pattern.matcher(content);
        if(matcher.find()) {
            fileName = matcher.group(2);
        }
        return fileName;
    }
    private void httpHeader(int statusCode,String contentType,int contentLength)
    {
        String versionAndStatus="HTTP/1.1 "+statusCode +"\r\n";
        String serverHeader="Server: My Java HTTP Server : 1.0\n";
        String fileContentType="Content-Type: "+contentType+"\r\n";
        String fileContentLength=" Content-length: "+contentLength;
        String closeConnection="Connection: close\r\n\n";

        try {
            socketOutputStream.write(versionAndStatus.getBytes()); // Version & status code
            socketOutputStream.write(serverHeader.getBytes());
            socketOutputStream.write(fileContentType.getBytes()); // The type of data
            socketOutputStream.write(fileContentLength.getBytes());
            socketOutputStream.write(closeConnection.getBytes()); // Will close stream

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
