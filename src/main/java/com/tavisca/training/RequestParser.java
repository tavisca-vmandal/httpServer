package com.tavisca.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {

    public String parse(String content)
    {
        String requestedFile="";
        Pattern pattern=Pattern.compile("(.*)(\\s\\/)(.*)(\\sHTTP\\/\\d.\\d)");
        Matcher matcher=pattern.matcher(content);
        if(matcher.find()) {
            requestedFile = matcher.group(3);
        }
        return requestedFile;
    }
}
