package com.ivanmorett.simpletodo.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateConstants {
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateConstants.DATE_FORMAT, Locale.US);
}
