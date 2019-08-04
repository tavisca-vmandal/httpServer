package com.tavisca.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {

    public String parse(String content)
    {
        String requestedFile="";
        Pattern pattern=Pattern.compile("(.*)\\s\\/(.*)(HTTP\\/1\\.1)");
        Matcher matcher=pattern.matcher(content);
        if(matcher.find()) {
            requestedFile = matcher.group(2);
        }
        return requestedFile;
    }
}
