package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceT;
//to pass this test choose hsqldb profile im Maven tab
@ActiveProfiles({Profiles.JDBC, Profiles.HSQL_DB})
public class UserJdbcTestHsql extends UserServiceT {
}
