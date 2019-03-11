package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceT;

@ActiveProfiles({Profiles.JDBC, Profiles.POSTGRES_DB})
public class UserJdbcTestPostgress extends UserServiceT {
}
