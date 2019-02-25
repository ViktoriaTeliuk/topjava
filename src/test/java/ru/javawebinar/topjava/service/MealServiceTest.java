package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USERS_MEAL_ID_1, USER_ID);
        assertMatch(meal, USERS_MEAL_1);
    }

    @Test
    public void delete() {
        service.delete(USERS_MEAL_ID_1, USER_ID);
        assertMatch(service.getAll(USER_ID), USERS_MEALS_LIST_WITHOUT_MEAL_1);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(DATE_FROM, DATE_TO, USER_ID),
                USERS_MEALS_LIST_FILTERED_WITH_DATES);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), ADMINS_MEALS_LIST);
    }

    @Test
    public void update() {
        Meal updated = new Meal();
        updated.setId(USERS_MEAL_ID_1);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("New description");
        updated.setCalories(555);
        service.update(updated, USER_ID);
        assertMatch(service.get(USERS_MEAL_ID_1, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal created = new Meal(LocalDateTime.now(), "new meal", 444);
        Meal createdDB = service.create(created, USER_ID);
        created.setId(createdDB.getId());
        assertMatch(createdDB, created);
    }

    @Test(expected = NotFoundException.class)
    public void deleteSomeoneElses() {
        service.delete(USERS_MEAL_ID_2, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateSomeoneElses() {
        service.update(USERS_MEAL_3, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneElses() {
        service.get(USERS_MEAL_ID_4, ADMIN_ID);
    }
}