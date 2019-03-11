package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceT;

@ActiveProfiles(Profiles.JDBC)
public class MealJdbcTest extends MealServiceT {
}
