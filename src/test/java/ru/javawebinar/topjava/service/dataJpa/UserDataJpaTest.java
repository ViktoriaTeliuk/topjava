package ru.javawebinar.topjava.service.dataJpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceT;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(value = Profiles.DATAJPA)
public class UserDataJpaTest extends UserServiceT {
    @Test
    public void  getWithMealGr() {
        User user = service.getWithMealGr(USER_ID);
        MealTestData.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void  getWithMEalQwr() {
        User user = service.getWithMEalQwr(USER_ID);
        MealTestData.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void  getWithMealByReading() {
        User user = service.getWithMealByReading(USER_ID);
        MealTestData.assertMatch(user.getMeals(), MEALS);
    }

}
