package ru.javawebinar.topjava.service.dataJpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(value = Profiles.DATAJPA)
public class UserDataJpaTest extends UserServiceTest {
    @Test
    public void getWithMealGr() {
        User user = service.getWithMeals(USER_ID);
        UserTestData.assertMatch(user, USER);
        MealTestData.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void getWithoutMeal() {
        User user = service.getWithMeals(USER2_ID);
        UserTestData.assertMatch(user, USER2);
        MealTestData.assertMatch(user.getMeals(), List.of());
    }
}
