package com.urise.utill;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static YearMonth parse(String date) {
        if (ServletUtil.isEmpty(date)) {
            return YearMonth.now();
        }
        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
        return yearMonth;
    }
}
