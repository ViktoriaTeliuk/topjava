package ru.javawebinar.topjava.model;


import java.time.LocalDateTime;

public interface IMeal {

    LocalDateTime getDateTime();

    String getDescription();

    int getCalories();

    int getId();
}
