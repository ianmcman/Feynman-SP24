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
                    request.setAttribute("user", userToEdit);
                    url = "/Admin/editUser.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    String test = getServletContext().getContextPath();
                    return;
                } catch (SQLException e) {
                    response.sendRedirect("Private?action=dashboard");
                    return;
                }
            case "delete":
                int userToDeleteUserID = Integer.parseInt(request.getParameter("userID"));
                try {
                    int rowsAffected = FeynmanDB.deleteUser(userToDeleteUserID);
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
