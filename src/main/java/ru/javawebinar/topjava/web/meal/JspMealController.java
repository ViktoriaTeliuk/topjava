package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String all(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @PostMapping
    public String getFiltered(HttpServletRequest request) {
        request.setAttribute("meals", getBetween(parseLocalDate(request.getParameter("startDate")),
                parseLocalTime(request.getParameter("startTime")),
                parseLocalDate(request.getParameter("endDate")),
                parseLocalTime(request.getParameter("endTime"))));
        return "meals";
    }

    @GetMapping("/mealForm")
    public String showFormForAdd(@RequestParam("id") Integer id, Model model) {
        final Meal meal = id == null ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) : get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if ("".equals(request.getParameter("id")))
            create(meal);
        else
            update(meal, getId(request));

        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") int id) {
        delete(id);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
