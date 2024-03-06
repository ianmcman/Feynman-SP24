package controllers;

import business.User;
import business.Validation;
import data.FeynmanDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Admin extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Admin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Admin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        if (!isAdmin(request, response)) {
            response.sendRedirect("Public");
            return;
        }
        
        
        String action = request.getParameter("action");
        ArrayList<String> errors = new ArrayList<>();
        String url = new String();
        
        if (action == null) {
            action = "dashboard";
        }
        
        switch (action) {
            case "dashboard":
                try {
                    ArrayList<User> users = FeynmanDB.getAllUsers();
                    request.setAttribute("users", users);
                    url = "/Admin/dashboard.jsp";
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (SQLException e) {
                    getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }
            case "edit":
                int userToEditUserID = Integer.parseInt(request.getParameter("userID"));
                try {
                    User userToEdit = FeynmanDB.getUser(userToEditUserID);
                    request.setAttribute("userToEdit", userToEdit);
                    url = "/Admin/editUser.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (SQLException e) {
                    response.sendRedirect("Private?action=dashboard");
                    return;
                }
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
        if (!isAdmin(request, response)) {
            response.sendRedirect("Public");
            return;
        }
        
        String action = request.getParameter("action");
        ArrayList<String> errors = new ArrayList<>();
        String url = new String();
        
        if (action == null) {
            action = "dashboard";
        }
        
        switch (action) {
            case "delete":
                int userToDeleteUserID = Integer.parseInt(request.getParameter("userID"));
                try {
                    int rowsAffected = FeynmanDB.deleteUser(userToDeleteUserID);
                    response.sendRedirect("Private?action=dashboard");
                    return;
                } catch (SQLException e) {
                    response.sendRedirect("Private?action=dashboard");
                    return;
                }
            case "edit":
                int userID;
                String message = "";
                boolean isValid = true;
                
                String userIDParam = request.getParameter("userID");
                if (userIDParam != null && !userIDParam.isEmpty()) {
                    try {
                        userID = Integer.parseInt(userIDParam);
                    } catch (NumberFormatException e) {
                        message = "User Update Unsuccessful. Problem parsing userID.";
                        request.setAttribute("message", message);
                        response.sendRedirect("Private");
                        return;
                    }
                } else {
                    message = "User Update Unsuccessful. No userID passed.";
                    request.setAttribute("message", message);
                    response.sendRedirect("Private");
                    return;
                }
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                
                ArrayList<String> roles = new ArrayList<>();
                String[] rolesArray = request.getParameterValues("roles");
                if (rolesArray != null) {
                    roles.addAll(Arrays.asList(rolesArray));
                } else {
                    message += "User Update Unsuccessful. A user must have at least one role specified.";
                    isValid = false;
                }

                
                if (!isValid) {
                    try {
                        User userToEdit = FeynmanDB.getUser(userID);
                        request.setAttribute("userToEdit", userToEdit);
                        request.setAttribute("message", message);
                        url = "/Admin/editUser.jsp";
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (SQLException e) {
                        response.sendRedirect("Private?action=dashboard");
                        return;
                    }
                }
                
                
                User user = new User(userID, username, password, roles, firstName, lastName);
                try {
                    FeynmanDB.updateUser(user);
                    response.sendRedirect("Private?action=dashboard");
                    message = "User Update Successful";
                    request.setAttribute("message", message);
                    return;
                } catch (SQLException e) {
                    message += "User Update Unsuccessful";
                    request.setAttribute("message", message);
                    url = "/Admin/editUser.jsp";
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }
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
    }// </editor-fold>

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) {
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null || !loggedInUser.getRoles().contains("admin")) {
            return false;
        } else {
            return true;
        }
    }
}
