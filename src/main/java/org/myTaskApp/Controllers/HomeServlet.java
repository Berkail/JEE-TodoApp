package org.myTaskApp.Controllers;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if a requested URL is saved in session
        String requestedUrl = (String) request.getSession().getAttribute("requestedUrl");

        // Check if the requested URL is not null, not empty, and not /Home to avoid redirect loop
        if (requestedUrl != null && !requestedUrl.isEmpty() && !requestedUrl.equals(request.getContextPath() + "/Home")) {
            // Redirect to the requested URL if it's valid
            request.getSession().removeAttribute("requestedUrl");
            response.sendRedirect(requestedUrl);
        } else {
            // Otherwise, forward to index.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}

