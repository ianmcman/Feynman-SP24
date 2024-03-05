package controllers;

import business.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Private extends HttpServlet {

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
            out.println("<title>Servlet Private</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Private at " + request.getContextPath() + "</h1>");
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
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Public");
            return;
        }
        
 
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }
        
        switch (action) {
            case "default":
                break;
            case "dashboard":
                if (user.getRoles().contains("admin")) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Admin");
                    dispatcher.forward(request, response);
                    return;
                } else if (user.getRoles().contains("teacher")) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Teacher");
                    dispatcher.forward(request, response);
                    return;
                } else if (user.getRoles().contains("student")) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Student");
                    dispatcher.forward(request, response);
                    return;
                } else if (user.getRoles().contains("parent")) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Parent");
                    dispatcher.forward(request, response);
                    return;
                }                  
                break;
        }
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
        processRequest(request, response);
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

}
