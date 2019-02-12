package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal implements IMeal {
    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }
    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
    @Override
    public int getId() {
        return id;
    }

}
