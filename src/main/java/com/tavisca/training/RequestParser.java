package com.tavisca.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    private String requestedFile="";

    public String getRequestedFile(){
        return requestedFile;
    }

    public void parseRequest(String request)
    {
        Pattern pattern=Pattern.compile("(.*)(\\s\\/)(.*)(\\sHTTP\\/\\d.\\d)");
        Matcher matcher=pattern.matcher(request);
        if(matcher.find()) {
            requestedFile = matcher.group(3);
        }
    }
}
