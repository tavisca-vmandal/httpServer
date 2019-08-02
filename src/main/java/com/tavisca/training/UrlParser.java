package com.tavisca.training;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {

    public String parse(String content)
    {
        String fileName="";
        Pattern pattern=Pattern.compile("(.*)\\s\\/(.*)(HTTP\\/1\\.1)");
        Matcher matcher=pattern.matcher(content);
        if(matcher.find()) {
            fileName = matcher.group(2);
        }
        return fileName;
    }
}
