/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import data.FeynmanDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
                User user = new User();
                
                try {
                   user = FeynmanDB.authenticateCredentials(username, password); 
                } catch (SQLException e) {
                    
                }
                
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    url="/index.jsp";
                } else {
                    message = "Login Unsuccessful";
                    request.setAttribute("message", message);
                    url="/login.jsp";
                }
                break;
            case "register":
                username = request.getParameter("username");
                password = request.getParameter("password");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                user = new User(username, password, firstName, lastName);
                System.out.println(user);
                
                try {
                    int rowsAffected = FeynmanDB.registerUser(user);
                    if (rowsAffected == 1) {
                        // Add a redirect to login to save the user in the session or get the other user information and then save the user in the session.
                        url = "/index.jsp";
                    }
                } catch (SQLException e) {
                    errors.add(e + "\nProblem registering user.");
                    url = "/register.jsp";
                }
                
                
                break;
        }
        
        request.setAttribute("errors", errors);
      
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
