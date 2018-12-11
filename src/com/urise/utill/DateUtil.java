package com.urise.utill;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
