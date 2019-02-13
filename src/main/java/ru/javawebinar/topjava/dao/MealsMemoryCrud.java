package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MealsMemoryCrud implements IMealsCrud {

    private static ConcurrentHashMap<Integer, Meal> mealsMap = new ConcurrentHashMap<>(
            MealsUtil.getMealsList()
                    .stream()
                    .collect(Collectors.toMap(Meal::getId, Function.identity())));


    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());}


    private static AtomicInteger nextID = new AtomicInteger(mealsMap.size());
    static int incrementAndGetID() {
            return nextID.incrementAndGet();
        }

    @Override
    public Meal add(Meal meal) {
        int id = incrementAndGetID();
        return mealsMap.put(id, new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories())); // create new Meal to fill id
    }

    @Override
    public Meal edit(Meal meal) {
        return mealsMap.replace(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return mealsMap.get(id);
    }


}
