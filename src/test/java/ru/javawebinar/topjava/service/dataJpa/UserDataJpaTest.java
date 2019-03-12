package ru.javawebinar.topjava.service.dataJpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER2_ID;

@ActiveProfiles(value = Profiles.DATAJPA)
public class UserDataJpaTest extends UserServiceTest {
    @Test
    public void getWithMealGr() {
        User user = service.getWithMeals(USER_ID);
        MealTestData.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void getWithoutMeal() {
        User user = service.getWithMeals(USER2_ID);
        MealTestData.assertMatch(user.getMeals(), List.of());
    }
}
