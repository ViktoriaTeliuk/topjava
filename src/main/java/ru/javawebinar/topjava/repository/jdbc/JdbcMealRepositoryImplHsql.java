package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile(Profiles.HSQL_DB)
@Repository
public class JdbcMealRepositoryImplHsql extends JdbcMealRepositoryImpl {

    @Override
    protected Timestamp getNeededTypeDate(LocalDateTime dt) {
        return Timestamp.valueOf(dt);
    }

}
