package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.IMeal;
import ru.javawebinar.topjava.model.IMealsCrud;
import ru.javawebinar.topjava.util.MealsMemoryCrud;
import ru.javawebinar.topjava.util.TimeUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class EditMealsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        IMealsCrud mealsCrud = MealsMemoryCrud.getInstance();
        String forward;
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("id"));
            mealsCrud.DeleteMeal(mealId);
            forward = "/meals.jsp";
            request.setAttribute("mealsTo", ((MealsMemoryCrud)mealsCrud).getMealToList());
        } else if (action.equalsIgnoreCase("edit")){
            forward = "/editMeal.jsp";
            int mealId = Integer.parseInt(request.getParameter("id"));
            IMeal meal = mealsCrud.getMealById(mealId);
            request.setAttribute("meal", meal);
        } else {
            forward = "/editMeal.jsp";
        }
        if (action.equalsIgnoreCase("delete"))
            resp.sendRedirect("meals");
        else
            request.getRequestDispatcher(forward).forward(request, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String descr = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        LocalDateTime dt = LocalDateTime.parse(req.getParameter("dateTime"), TimeUtil.DATE_TIME_FORMATTER);

        IMealsCrud mealsCrud = MealsMemoryCrud.getInstance();

        String mealID = req.getParameter("id");
        if(mealID == null || mealID.isEmpty())
            mealsCrud.AddMeal(0, dt, descr, calories);
        else
            mealsCrud.EditMeal(Integer.parseInt(mealID), dt, descr, calories);

        req.setAttribute("mealsTo", ((MealsMemoryCrud)mealsCrud).getMealToList());

        resp.sendRedirect("meals");

    }
}
