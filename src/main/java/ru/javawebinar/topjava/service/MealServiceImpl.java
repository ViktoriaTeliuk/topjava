package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userID) {
        checkNew(meal);
        checkUsrIDAreEqual(meal.getUserID(), userID);
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        checkUsrIDAreEqual(get(id, userID).getUserID(), userID);
        repository.delete(id);
    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        Meal meal = repository.get(id);
        checkNotFoundWithId(meal, id);
        checkUsrIDAreEqual(meal.getUserID(), userID);
        return ValidationUtil.checkNotFoundWithId(meal, id);
    }

    @Override
    public void update(Meal meal, int userID) {
        checkUsrIDAreEqual(meal.getUserID(), userID);
        repository.save(meal);
    }

    @Override
    public List<Meal> getAll(int userID) {
        return new ArrayList<>(repository.getAll(userID));
    }

    @Override
    public List<Meal> getFilteredList(int userID, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return  new ArrayList<>(repository.getFilteredList(userID, startDate, endDate, startTime, endTime));
    }
}