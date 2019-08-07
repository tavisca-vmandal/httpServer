package com.tavisca.training;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyFileHandler {
    private Header header;
    private String requestedFile;

    public MyFileHandler(Header header, String requestedFile) {
        this.header = header;
        this.requestedFile = requestedFile;
    }

    public void respondFile(String requestedFile, BufferedOutputStream socketOutputStream) throws IOException {
        String absoluteFilePath="files/"+requestedFile;
        FileInputStream fileInputStream = new FileInputStream(absoluteFilePath);
        int contentSize = fileInputStream.available();
        byte[] buffer = new byte[contentSize];
        fileInputStream.read(buffer);
        header.sendHeader(socketOutputStream);
        socketOutputStream.write(buffer);
        socketOutputStream.close();
    }

    public String getContentType() {
        String contentType;
        if (requestedFile.contains(".")) {
            String[] extractFileExtension = requestedFile.split("\\.");
            contentType = extractFileExtension[1];
        } else
            contentType = "html";

        return contentType;
    }

    public long getContentLength() {
        File file = new File(requestedFile);
        long contentLength = file.length();
        return contentLength;
    }
}
