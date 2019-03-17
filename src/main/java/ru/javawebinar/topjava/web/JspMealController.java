package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExcess(mealService.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @PostMapping
    public String getFiltered(HttpServletRequest request) {
        request.setAttribute("meals", MealsUtil.getFilteredWithExcess(
                mealService.getBetweenDates(parseLocalDate(request.getParameter("startDate")), parseLocalDate(request.getParameter("endDate")), SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(), parseLocalTime(request.getParameter("startTime")), parseLocalTime(request.getParameter("endTime"))));
        return "meals";
    }

    @GetMapping("/mealForm")
    public String showFormForAdd(@RequestParam("id") Integer id, Model model) {
        final Meal meal = id == null ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                mealService.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/saveMeal")
    public String saveCustomer(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").equals(""))
            mealService.create(meal, SecurityUtil.authUserId());
        else {
            meal.setId(getId(request));
            mealService.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        mealService.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
