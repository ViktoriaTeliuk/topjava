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
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, USERS_MEALS_LIST.get(USERS_MEALS_LIST.size() - 1));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), USERS_MEALS_LIST.subList(0, USERS_MEALS_LIST.size()-1));
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(DATE_FROM, DATE_TO, USER_ID),
                USERS_MEALS_LIST.stream()
                        .filter(meal -> Util.isBetween(meal.getDateTime().toLocalDate(), DATE_FROM.toLocalDate(), DATE_TO.toLocalDate()))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), ADMINS_MEALS_LIST);
    }

    @Test
    public void update() {
        Meal updated = new Meal();
        updated.setId(MEAL_ID);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("New description");
        updated.setCalories(555);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
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
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateSomeoneElses() {
        service.update(USERS_MEALS_LIST.get(0), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneElses() {
        service.get(MEAL_ID, ADMIN_ID);
    }
}