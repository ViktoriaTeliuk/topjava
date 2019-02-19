package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkUsrIDAreEqual;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach( meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new HashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        checkUsrIDAreEqual(get(meal.getId(), userId).getUserId(), userId);
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(int id, int userId) {
        checkUsrIDAreEqual(get(id, userId).getUserId(), userId);
        repository.get(userId).remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mapMeals = repository.get(userId);
        checkNotFoundWithId(mapMeals, userId);
        Meal meal = mapMeals.get(id);
        checkNotFoundWithId(meal, id);
        checkUsrIDAreEqual(meal.getUserId(), userId);
        return  meal;
    }

    private Comparator<Meal> comparatorDateReverse = Comparator.comparing(Meal::getDateTime).reversed();

    private Stream<Meal> getListForUser(int userId) {
        return repository.get(userId).values().stream();
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getListForUser(userId).sorted(comparatorDateReverse).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredList(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getListForUser(userId)
                .filter(meal -> (DateTimeUtil.isBetween(meal.getDate(), startDate, LocalDate.MAX)) &&
                        (DateTimeUtil.isBetween(meal.getDate(), LocalDate.MIN, endDate)) &&
                        (DateTimeUtil.isBetween(meal.getTime(), startTime, LocalTime.MAX)) &&
                        (DateTimeUtil.isBetween(meal.getTime(), LocalTime.MIN, endTime)))
                .sorted(comparatorDateReverse)
                .collect(Collectors.toList());
    }

}

