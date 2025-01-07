package org.myTaskApp.Controllers;

import java.io.IOException;

import org.myTaskApp.Entities.User;
import org.myTaskApp.Services.UserService;
import org.myTaskApp.Util.FormChecker;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


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
        if (request.getSession().getAttribute("userId") != null) {
        	response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        request.getRequestDispatcher("/Home").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confPassword");
        
        
        if(!FormChecker.areFieldsFilled(username, email, password, confirmPassword)) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        if(!FormChecker.isValidEmail(email))
        {
        	request.setAttribute("error", "Email form is incorrect");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        
        if(!password.equals(confirmPassword))
        {
        	request.setAttribute("error", "Password and Confirm password must match");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try {
            // Attempt to create the user
            User user = userService.createUser(username, email, password);

            // Invalidate any old session and create a new one
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            
            session = request.getSession();
            session.setAttribute("userId", user.getId());
            
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (Exception e) {
            // Handle user creation errors (e.g., duplicate username or email)
            request.setAttribute("error", "Username or email already exists.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}