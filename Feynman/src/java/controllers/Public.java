package controllers;

import business.User;
import data.FeynmanDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Public extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/index.jsp";
        String action = request.getParameter("action");
        ArrayList<String> errors = new ArrayList();
        String message = "";
        
        if (action == null) {
            action = "default";
        }
        
        switch (action) {
            case "default":
                break;
            case "login":
                url="/login.jsp";
                break;
            case "register":
                url = "/register.jsp";
                break;
        }
        
        request.setAttribute("errors", errors);
      
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/index.jsp";
        String action = request.getParameter("action");
        ArrayList<String> errors = new ArrayList();
        String message = "";
        
        if (action == null) {
            action = "default";
        }
        
        switch (action) {
            case "default":
                break;
            case "login":
                loginUser(request, response);
                return;
            case "register":
                registerUser(request, response);
                return;
        }
        
        request.setAttribute("errors", errors);
      
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        ArrayList<String> roles = new ArrayList<String>(Arrays.asList(request.getParameterValues("roles")));
        User user = new User(username, password, firstName, lastName, roles);
        
        try {
            FeynmanDB.registerUser(user);
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            loginUser(request, response);
        } catch (SQLException e) {
            String message = "Registration Unsuccessful";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    
    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getAttribute("username");
        if (username == null) {
            username = request.getParameter("username");
        }
        String password = (String) request.getAttribute("password");
        if (password == null) {
            password = request.getParameter("password");
        }
        User user = new User();
        
        try {
           user = FeynmanDB.authenticateCredentials(username, password); 
        } catch (SQLException ex) {
           Logger.getLogger(Public.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("Private?action=dashboard");
        } else {
            String message = "Login Unsuccessful";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
