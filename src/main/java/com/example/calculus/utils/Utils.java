package com.example.calculus.utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class Utils {

    public static String decodeBase64(String input) throws Exception {
        byte[] byteArray = Base64.decodeBase64(input.getBytes());

        String decodedString = new String(byteArray);

        if (decodedString.length() == 0) {
            throw new Exception("No expression specified");
        }

        return decodedString;
    }
}
