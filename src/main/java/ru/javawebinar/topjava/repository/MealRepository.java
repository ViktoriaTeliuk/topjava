package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int userID);

    Collection<Meal> getFilteredList(int userID, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
