package com.tavisca.training;

import java.io.*;
import java.net.Socket;

import static com.tavisca.training.MyLogger.logger;

public class ResponseHandler {

    private BufferedOutputStream socketOutputStream;
    private String requestedFile;
    private Header header;
    private MyFileHandler myFileHandler;

    public ResponseHandler(Socket socket, String requestedFile) throws IOException {
        socketOutputStream = new BufferedOutputStream(socket.getOutputStream());
        this.requestedFile = requestedFile;
        header = new Header();
        myFileHandler = new MyFileHandler(header, requestedFile);
    }

    public void sendResponse() {
        try {
            if (requestedFile.trim().isEmpty())
                sendDefaultResponse();
            else
                sendRequestedResponse();
        } catch (IOException e) {
            sendErrorResponse();
        }
    }

    private void setHeader(String status) {
        header.setStatus(status);
        header.setContentLength(myFileHandler.getContentLength());
        header.setContentType(myFileHandler.getContentType());
    }

    private void sendDefaultResponse() throws IOException {
        requestedFile = "index.html";
        setHeader("200");
        myFileHandler.respondFile(requestedFile, socketOutputStream);
    }

    private void sendErrorResponse() {
        requestedFile = "FileNotFound.html";
        setHeader("404");
        try {
            myFileHandler.respondFile(requestedFile, socketOutputStream);
        } catch (IOException e) {
            logger.warning("Error page:Not Found");
        }
    }

    private void sendRequestedResponse() throws IOException {
        setHeader("200");
        myFileHandler.respondFile(requestedFile, socketOutputStream);
    }
}
