package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceT;

@ActiveProfiles(Profiles.JPA)
public class MealJpaTest extends MealServiceT {
}
