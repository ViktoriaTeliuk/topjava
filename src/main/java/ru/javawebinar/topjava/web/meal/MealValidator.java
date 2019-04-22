package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Component
public class MealValidator implements Validator {

    @Autowired
    MealService mealService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;

        List<Meal> meals = mealService.getBetweenDateTimes(meal.getDateTime(), meal.getDateTime(), SecurityUtil.authUserId());
        if (meals.size() > 0)
            for (int i = 0; i < meals.size(); i++) {
                if (!meals.get(i).getId().equals(meal.getId())) {
                    errors.rejectValue("dateTime", "Meal with this date & time already exist");
                    break;
                }
            }
    }
}