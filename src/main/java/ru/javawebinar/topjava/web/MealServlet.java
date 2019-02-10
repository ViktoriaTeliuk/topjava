package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.Constants;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     //   resp.setContentType("text/html;charset=utf-8");
        List<MealTo> mealsTo = MealsUtil.getFilteredWithExcess(MealsUtil.fillMealToListWithDataHrdc(),
                LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealsTo", mealsTo);
        req.setAttribute("localDateTimeFormat", Constants.SMPL_DATE_FORMAT);
        //resp.sendRedirect("meals.jsp");
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
