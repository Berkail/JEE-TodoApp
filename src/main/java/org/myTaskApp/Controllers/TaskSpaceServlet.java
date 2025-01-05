package org.myTaskApp.Controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.myTaskApp.Entities.TaskSpace;
import org.myTaskApp.Entities.User;
import org.myTaskApp.Services.TaskSpaceService;

@WebServlet("/taskspace/*")
public class TaskSpaceServlet extends HttpServlet {
    @Inject
    private TaskSpaceService taskSpaceService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        try {
            Long taskSpaceId = Long.parseLong(pathInfo.substring(1));
            User user = (User) request.getSession().getAttribute("user");
            TaskSpace taskSpace = taskSpaceService.getTaskSpace(taskSpaceId, user);
            request.setAttribute("taskSpace", taskSpace);
            request.getRequestDispatcher("/WEB-INF/views/taskspace.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        try {
            taskSpaceService.createTaskSpace(user, title, description);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to create task space");
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        try {
            Long taskSpaceId = Long.parseLong(pathInfo.substring(1));
            User user = (User) request.getSession().getAttribute("user");
            TaskSpace taskSpace = taskSpaceService.getTaskSpace(taskSpaceId, user);
            taskSpaceService.deleteTaskSpace(taskSpace);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}