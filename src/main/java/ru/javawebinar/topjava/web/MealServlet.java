package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.IMealsCrud;
import ru.javawebinar.topjava.dao.MealsMemoryCrud;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private final IMealsCrud mealsCrud = new MealsMemoryCrud();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/meals.jsp";
        String action = req.getParameter("action");
        if (action == null)
            req.setAttribute("mealsTo", MealsUtil.getFilteredWithExcess(mealsCrud.getAll(),
                    LocalTime.MIN, LocalTime.MAX, 2000));
        else if (action.equalsIgnoreCase("edit")) {
            forward = "/editMeal.jsp";
            int mealId = Integer.parseInt(req.getParameter("id"));
            Meal meal = mealsCrud.getById(mealId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("insert"))
            forward = "/editMeal.jsp";
        else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("id"));
            mealsCrud.delete(mealId);
            req.setAttribute("mealsTo", MealsUtil.getFilteredWithExcess(mealsCrud.getAll(),
                    LocalTime.MIN, LocalTime.MAX, 2000));
            resp.sendRedirect("meals");
            return;
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String descr = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        LocalDateTime dt = LocalDateTime.parse(req.getParameter("dateTime"), TimeUtil.DATE_TIME_FORMATTER);


        String mealID = req.getParameter("id");
        if (mealID == null || mealID.isEmpty())
            mealsCrud.add(new Meal(0, dt, descr, calories));
        else
            mealsCrud.edit(new Meal(Integer.parseInt(mealID), dt, descr, calories));

        req.setAttribute("mealsTo", MealsUtil.getFilteredWithExcess(mealsCrud.getAll(),
                LocalTime.MIN, LocalTime.MAX, 2000));

        resp.sendRedirect("meals");
    }

}
