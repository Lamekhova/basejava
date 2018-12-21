package com.urise.utill;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static YearMonth parse(String date) {
        if (date == null || date.trim().length() == 0) {
            return YearMonth.now();
        }
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
