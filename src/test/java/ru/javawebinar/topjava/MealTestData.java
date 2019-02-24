package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEALID = START_SEQ + 2;
    public static final LocalDateTime DATEFROM = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);
    public static final LocalDateTime DATETO = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);

    public static final List<Meal> USERSMEALSLIST = Stream.of(
            new Meal(MEALID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000),
            new Meal(MEALID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(MEALID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(MEALID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
            new Meal(MEALID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
            new Meal(MEALID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());


    public static final List<Meal> ADMINSMEALSLIST = Stream.of(
            new Meal(MEALID + 6, LocalDateTime.of(2015, Month.JUNE, 14, 0, 0), "Админ ланч", 510),
            new Meal(MEALID + 7, LocalDateTime.of(2015, Month.JUNE, 21, 0, 0), "Админ ужин", 1500))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());


}
