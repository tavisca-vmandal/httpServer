package com.tavisca.training;

import java.io.*;
import java.net.Socket;


public class Response{

    private BufferedOutputStream socketOutputStream;
    private String requestedFile;
    MyLogger myLogger=new MyLogger();

    public Response(Socket socket, String requestedFile) throws IOException {

        socketOutputStream =new BufferedOutputStream(socket.getOutputStream());
        this.requestedFile =requestedFile;

    }
    public void sendResponse()  {

        try {
            if (requestedFile.trim().isEmpty())
                sendDefaultResponse();
            else
                sendRequestedResponse();

        } catch (IOException e) {
                respondErrorPage();
        }

    }

    private void sendDefaultResponse() throws IOException {

        requestedFile="index.html";
        String headerStatusCode="202 Ok";
        sendRequestedFile(requestedFile, headerStatusCode);

    }

    private void respondErrorPage() {

        requestedFile="FileNotFound.html";
        String headerStatusCode="404";
        try {
            sendRequestedFile(requestedFile, headerStatusCode);
        } catch (IOException e) {
            myLogger.log("Error page Not Found");
        }
    }

    private void sendRequestedResponse() throws IOException {

        String headerStatusCode="202 Ok";
        sendRequestedFile(requestedFile,headerStatusCode);
    }

    private void sendRequestedFile( String requestedFile,String statusCode) throws IOException {

            FileInputStream fileInputStream = new FileInputStream(requestedFile);
            int contentSize=fileInputStream.available();
            byte[] buffer=new byte[contentSize];
            fileInputStream.read(buffer);
            writeHttpHeader(statusCode);
            socketOutputStream.write(buffer);
            socketOutputStream.close();
    }

    private void writeHttpHeader(String statusCode)
    {
        String versionAndStatus="HTTP/1.1 "+statusCode +"\r\n";
        String serverHeader="Server: My Java HTTP Server : 1.0\n";
        String fileContentType="Content-Type: "+getContentType(requestedFile)+"\r\n";
        String fileContentLength="Content-length: "+getFileSize(requestedFile);
        String closeConnection="Connection: close\r\n\r\n";

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

    private String getContentType(String requestedFile) {
        try
        {
            String[] extractFileExtension = requestedFile.split("\\.");
            String contentType = extractFileExtension[1];
            return contentType;

        }catch (ArrayIndexOutOfBoundsException e)
        {
            return "html";
        }
    }

    public String getFileSize(String requestedFile)
    {
        File file =new File(requestedFile);
        long fileLength=file.length();
        return String.valueOf(fileLength);
    }
}
