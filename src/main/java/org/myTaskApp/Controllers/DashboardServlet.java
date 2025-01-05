package org.myTaskApp.Controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import org.myTaskApp.Entities.TaskSpace;
import org.myTaskApp.Entities.User;
import org.myTaskApp.Services.TaskSpaceService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Inject
    private TaskSpaceService taskSpaceService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<TaskSpace> taskSpaces = taskSpaceService.getUserTaskSpaces(user);
        request.setAttribute("taskSpaces", taskSpaces);
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}