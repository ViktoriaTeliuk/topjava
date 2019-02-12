package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.IMealsCrud;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.IMeal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealsMemoryCrud implements IMealsCrud {

    private static CopyOnWriteArrayList<IMeal> mealsList;

    private static MealsMemoryCrud ourInstance = new MealsMemoryCrud(MealsUtil.fillMealToListWithDataHrdc());
    public static IMealsCrud getInstance() {
        return ourInstance;
    }

    private MealsMemoryCrud(List<Meal> mealsList){
        if (MealsMemoryCrud.mealsList == null)
            MealsMemoryCrud.mealsList = new CopyOnWriteArrayList<>(mealsList);
    }

    public List<IMeal> getMealsList() {return mealsList;}

    public List<MealTo> getMealToList() {
        ArrayList lm = new ArrayList(mealsList);
        return MealsUtil.getFilteredWithExcess(lm,
                LocalTime.MIN, LocalTime.MAX, 2000);
    }

    //if id=0 uses Counter
    public IMeal AddMeal(int id, LocalDateTime dateTime, String description, int calories) {
        IMeal res = new Meal(id==0?Counter.incrementCount():id, dateTime, description, calories);
        mealsList.add(res);
        return res;
    }

    public IMeal EditMeal(int id, LocalDateTime dateTime, String description, int calories) {
        for (int i = 0; i < mealsList.size(); i++)
            if (mealsList.get(i).getId() == id)
                synchronized (this) {
                    mealsList.remove(i);
                    return AddMeal(id, dateTime, description, calories);
                }
        return null;
    }

    public void DeleteMeal(int id) {
        for (int i = 0; i < mealsList.size(); i++)
            if (mealsList.get(i).getId() == id) {
                mealsList.remove(i);
                break;
            }
    }

    public IMeal getMealById(int id) {
        for (IMeal iMeal : mealsList)
            if (iMeal.getId() == id)
                return iMeal;

        return null;
    }


}
