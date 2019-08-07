package com.tavisca.training;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class Header {
    private String status;
    private String contentType;
    private long contentLength;

    public void setStatus(String status) {
        this.status=status;
    }
    public void setContentLength(long contentLength) {
        this.contentLength=contentLength;
    }
    public void setContentType(String contentType) {
        this.contentType=contentType;
    }
    public void sendHeader(BufferedOutputStream socketOutputStream) {
        try {
            socketOutputStream.write(("HTTP/1.1 "+status +"\r\n").getBytes()); // Version & status code
            socketOutputStream.write(("Server: My Java HTTP Server : 1.0\n").getBytes());
            socketOutputStream.write(("Content-Type: "+contentType+"\r\n").getBytes()); // The type of data
            socketOutputStream.write(("Content-length: "+contentLength).getBytes());
            socketOutputStream.write(("Connection: close\r\n\r\n").getBytes()); // Will close stream

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
