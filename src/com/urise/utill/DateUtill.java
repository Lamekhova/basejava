package com.urise.utill;

import java.time.LocalDate;
import java.time.Month;

public class DateUtill {

    LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
