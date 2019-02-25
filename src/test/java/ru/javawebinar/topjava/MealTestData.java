package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {


    public static final LocalDateTime DATE_FROM = LocalDateTime.of(2015, Month.MAY, 30, 0, 0);
    public static final LocalDateTime DATE_TO = LocalDateTime.of(2015, Month.MAY, 30, 23, 59);

    public static final int USERS_MEAL_ID_1 = START_SEQ + 2;
    public static final Meal USERS_MEAL_1 = new Meal(USERS_MEAL_ID_1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 1000);
    public static final int USERS_MEAL_ID_2 = START_SEQ + 3;
    public static final Meal USERS_MEAL_2 = new Meal(USERS_MEAL_ID_2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500);
    public static final int USERS_MEAL_ID_3 = START_SEQ + 4;
    public static final Meal USERS_MEAL_3 = new Meal(USERS_MEAL_ID_3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final int USERS_MEAL_ID_4 = START_SEQ + 5;
    public static final Meal USERS_MEAL_4 = new Meal(USERS_MEAL_ID_4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final int USERS_MEAL_ID_5 = START_SEQ + 6;
    public static final Meal USERS_MEAL_5 = new Meal(USERS_MEAL_ID_5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final int USERS_MEAL_ID_6 = START_SEQ + 7;
    public static final Meal USERS_MEAL_6 = new Meal(USERS_MEAL_ID_6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final int USERS_MEAL_ID_7 = START_SEQ + 8;
    public static final Meal ADMINS_MEAL_7 = new Meal(USERS_MEAL_ID_7, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final int ADMINS_MEAL_ID_8 = START_SEQ + 9;
    public static final Meal ADMINS_MEAL_8 = new Meal(ADMINS_MEAL_ID_8, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> USERS_MEALS_LIST = Arrays.asList(USERS_MEAL_6, USERS_MEAL_5, USERS_MEAL_4, USERS_MEAL_3, USERS_MEAL_2, USERS_MEAL_1);

    public static final List<Meal> ADMINS_MEALS_LIST = Arrays.asList(ADMINS_MEAL_8, ADMINS_MEAL_7);

    public static final List<Meal> USERS_MEALS_LIST_WITHOUT_MEAL_1 = USERS_MEALS_LIST.subList(0, USERS_MEALS_LIST.size()-1);
    public static final List<Meal> USERS_MEALS_LIST_FILTERED_WITH_DATES = USERS_MEALS_LIST.stream()
                        .filter(meal -> Util.isBetween(meal.getDateTime().toLocalDate(), DATE_FROM.toLocalDate(), DATE_TO.toLocalDate()))
            .collect(Collectors.toList());

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
