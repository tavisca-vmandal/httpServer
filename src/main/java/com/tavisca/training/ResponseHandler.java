package com.tavisca.training;

import java.io.*;
import java.net.Socket;

public class Response{

    private BufferedOutputStream socketOutputStream;
    private String requestedFile;
    private Header header=new Header();

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
                sendErrorResponse();
        }
    }
    private void setHeader(String status,String requestedFile) {
        header.setStatus(status);
        header.setContentLength(getContentLength(requestedFile));
        header.setContentType(getContentType(requestedFile));
    }
    private void sendDefaultResponse() throws IOException {
        requestedFile="index.html";
        setHeader("200",requestedFile);
        sendRequestedFile(requestedFile);
    }
    private void sendErrorResponse() {
        requestedFile="FileNotFound.html";
        setHeader("404",requestedFile);
        try {
            sendRequestedFile(requestedFile);
        } catch (IOException e) {
            MyLogger.log("Error page Not Found");
        }
    }
    private void sendRequestedResponse() throws IOException {

        setHeader("200",requestedFile);
        sendRequestedFile(requestedFile);
    }
    private void sendRequestedFile( String requestedFile) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(requestedFile);
        int contentSize=fileInputStream.available();
        byte[] buffer=new byte[contentSize];
        fileInputStream.read(buffer);
        header.sendHeader(socketOutputStream);
        socketOutputStream.write(buffer);
        socketOutputStream.close();
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
    public long getContentLength(String requestedFile) {
        File file =new File(requestedFile);
        long fileLength=file.length();
        return fileLength;
    }
}
