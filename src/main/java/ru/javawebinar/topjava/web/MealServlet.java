package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.crud.MealStorageInMemory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    MealStorageInMemory storage = new MealStorageInMemory();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.debug("redirect to meal");

        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        switch (action) {
            case "add":
                request.getRequestDispatcher("addMeal.jsp").forward(request, response);
                break;
            case "delete":
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");
        request.getParameter("");
    }
}
