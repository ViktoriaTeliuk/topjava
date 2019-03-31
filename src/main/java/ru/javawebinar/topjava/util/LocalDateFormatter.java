package ru.javawebinar.topjava.util;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class LocalDateFormatter implements Formatter<LocalDate> {

    private String pattern;

    public LocalDateFormatter(String pattern) {
        this.pattern = pattern;
    }

    public String print(LocalDate date, Locale locale) {
        return (date == null) ? "" : date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public LocalDate parse(String formatted, Locale locale) throws ParseException {
        return (formatted.length() == 0) ? null : LocalDate.parse(formatted);
    }
}

