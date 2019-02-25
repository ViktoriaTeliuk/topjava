package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 2;
    public static final LocalDateTime DATE_FROM = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);
    public static final LocalDateTime DATE_TO = LocalDateTime.of(2015, Month.MAY, 30, 23, 59);

    public static final List<Meal> USERS_MEALS_LIST = Stream.of(
            new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000),
            new Meal(MEAL_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
            new Meal(MEAL_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
            new Meal(MEAL_ID + 4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID + 5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> ADMINS_MEALS_LIST = Stream.of(
            new Meal(MEAL_ID + 6, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510),
            new Meal(MEAL_ID + 7, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        Assert.assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertMatch(actual.get(i), expected.get(i));
        }
    }
}
