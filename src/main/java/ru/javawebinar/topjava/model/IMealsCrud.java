package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public interface IMealsCrud {
    IMeal AddMeal(int id, LocalDateTime dateTime, String description, int calories);

    IMeal EditMeal(int id, LocalDateTime dateTime, String description, int calories);

    void DeleteMeal(int id);

    IMeal getMealById(int id);
}
