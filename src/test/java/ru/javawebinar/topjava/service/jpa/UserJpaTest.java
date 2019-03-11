package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceT;

@ActiveProfiles(Profiles.JPA)
public class UserJpaTest extends UserServiceT {
}
