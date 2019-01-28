package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 9, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 11, 0), "Второй завтрак", 300),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 19, 0), "Ужин", 1600)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> result = new ArrayList<>();
        Collections.sort(mealList, new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

        List<UserMeal> userMealListForOneDay = new ArrayList<>();
        int calories = 0;

        for (int i = 0; i < mealList.size(); i++) {
            UserMeal um = mealList.get(i);

            if (TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)) //to add only needed records
                userMealListForOneDay.add(um);
            calories += um.getCalories();

            // now we have all data to add info about one day to result list
            if ((i == mealList.size() - 1) || (!mealList.get(i + 1).getDateTime().toLocalDate().equals(um.getDateTime().toLocalDate()))) {
                for (UserMeal userMeal : userMealListForOneDay
                ) {
                    result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), calories > caloriesPerDay));
                }
                calories = 0;
                userMealListForOneDay.clear();
            }
        }

        return result;
    }
}
