package com.urise.utill;

public class ServletUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
