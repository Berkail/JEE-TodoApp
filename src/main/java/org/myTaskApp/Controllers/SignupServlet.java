package org.myTaskApp.Controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.myTaskApp.Entities.User;
import org.myTaskApp.Services.UserService;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
    @Inject
    private UserService userService;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is already logged in
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("dashboard");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confPassword");
        
        // Validate input
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
            return;
        }
        
        try {
            User user = userService.createUser(username, email, password);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("dashboard");
        } catch (Exception e) {
            request.setAttribute("error", "Username or email already exists");
            request.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        }
    }
}