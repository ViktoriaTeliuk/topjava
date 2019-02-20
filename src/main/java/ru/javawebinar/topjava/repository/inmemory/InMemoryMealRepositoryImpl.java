package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new HashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        Map<Integer, Meal> mapMeals = repository.get(userId);
        if (mapMeals == null)
            throw new NotFoundException("Meal not found for userId = " + userId);

        return mapMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mapMeals = repository.get(userId);
        if (mapMeals == null) return false;
        return (mapMeals.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mapMeals = repository.get(userId);
        if (mapMeals == null) return null;
        Meal meal = mapMeals.get(id);
        if (meal == null) return null;
        if (meal.getUserId() != userId)
            throw new NotFoundException("Wrong userId");
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getListForUser(userId, meal -> true);
    }

    @Override
    public List<Meal> getFilteredList(int userId, LocalDate startDate, LocalDate endDate) {
        return getListForUser(userId, meal -> (DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)));
    }

    private List<Meal> getListForUser(int userId, Predicate<Meal> filter) {
        return repository.get(userId).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

