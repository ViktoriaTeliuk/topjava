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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;
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
        Meal meal = service.get(MEALID, USER_ID);
        assertThat(meal).isEqualToComparingFieldByField(USERSMEALSLIST.get(USERSMEALSLIST.size() - 1));
    }

    @Test
    public void delete() {
        int count = service.getAll(USER_ID).size();
        service.delete(MEALID, USER_ID);
        if (count - 1 != service.getAll(USER_ID).size())
            throw new IllegalStateException("Meal was not deleted id=" + MEALID);
    }

    @Test
    public void getBetweenDateTimes() {
        assertEquals("",
                USERSMEALSLIST.stream()
                        .filter(meal -> Util.isBetween(meal.getDateTime().toLocalDate(), DATEFROM.toLocalDate(), DATETO.toLocalDate()))
                        .collect(Collectors.toList()),
                service.getBetweenDateTimes(DATEFROM, DATETO, USER_ID));
    }

    @Test
    public void getAll() {
        assertEquals("", ADMINSMEALSLIST, service.getAll(ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = new Meal();
        updated.setId(MEALID);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("New description");
        updated.setCalories(555);
        service.update(updated, USER_ID);
        assertThat(service.get(MEALID, USER_ID)).isEqualToIgnoringGivenFields(updated);
    }

    @Test
    public void create() {
        Meal created = new Meal(LocalDateTime.now(), "new meal", 444);
        Meal createdDB = service.create(created, USER_ID);
        created.setId(createdDB.getId());
        assertThat(createdDB).isEqualToComparingFieldByField(created);
    }

    @Test(expected = NotFoundException.class)
    public void deleteSomeoneElses() {
        service.delete(MEALID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateSomeoneElses() {
        service.update(USERSMEALSLIST.get(0), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getSomeoneElses() {
        service.get(MEALID, ADMIN_ID);
    }
}