package ru.javawebinar.topjava.service.dataJpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceT;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class MealDataJpaTest extends MealServiceT {
    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        MealTestData.assertMatch(meal, MEAL1);
        UserTestData.assertMatch(meal.getUser(), USER);
    }

    @Test
    public void getWithUserQwr() {
        Meal meal = service.getWithUserQwr(MEAL1_ID, USER_ID);
        MealTestData.assertMatch(meal, MEAL1);
        UserTestData.assertMatch(meal.getUser(), USER);
    }

}
