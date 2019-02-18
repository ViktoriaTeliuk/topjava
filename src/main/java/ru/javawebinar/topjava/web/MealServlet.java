package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        if (appCtx != null)
            appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                SecurityUtil.authUserId(),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew())
            mealRestController.create(meal);
        else
            mealRestController.update(meal, Integer.valueOf(id));
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userID = request.getParameter("selectUser");
        if (userID != null)
          SecurityUtil.setAuthUserId(Integer.parseInt(userID));

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                if (request.getParameter("dateFrom") != null) {
                    LocalDate dateFrom = request.getParameter("dateFrom").isEmpty() ? null : LocalDate.parse(request.getParameter("dateFrom"));
                    LocalDate dateTo = request.getParameter("dateTo").isEmpty() ? null : LocalDate.parse(request.getParameter("dateTo"));
                    LocalTime timeFrom = request.getParameter("timeFrom").isEmpty() ? null : LocalTime.parse(request.getParameter("timeFrom"));
                    LocalTime timeTo = request.getParameter("timeTo").isEmpty() ? null : LocalTime.parse(request.getParameter("timeTo"));
                    log.info("filter");
                    request.setAttribute("meals", mealRestController.getMealToList(
                            mealRestController.getFilteredList(dateFrom, dateTo, timeFrom, timeTo, null), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                    //to save filter params on form
                    request.setAttribute("dateFrom", dateFrom);
                    request.setAttribute("dateTo", dateTo);
                    request.setAttribute("timeFrom", timeFrom);
                    request.setAttribute("timeTo", timeTo);
                } else {
                    log.info("getAll");
                    request.setAttribute("meals",
                            mealRestController.getMealToList(mealRestController.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                }
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
