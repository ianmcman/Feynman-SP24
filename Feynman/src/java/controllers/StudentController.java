package controllers;

import business.Attempt;
import business.QuestionPool;
import business.User;
import data.FeynmanDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class StudentController extends HttpServlet {

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
        
        if (!isStudent(request, response)) {
            response.sendRedirect("Public");
            return;
        }
        
        String url = "/Student/index.jsp";
        String action = request.getParameter("action");
        User loggedInUser = (User) request.getSession().getAttribute("user");
        
        ArrayList<String> errors = new ArrayList();
        String message = "";

        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "studentAttempts":
                url = "/Student/studentAttempts.jsp";
                List<Attempt> studentAttempts = FeynmanDB.getStudentAttempts(loggedInUser.getUserID());
                System.out.println(studentAttempts);
                request.setAttribute("studentAttempts", studentAttempts);
            default:
                break;
        }
        
        
        request.setAttribute("message", message);
        request.setAttribute("errors", errors);

        getServletContext().getRequestDispatcher(url).forward(request, response);
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
        processRequest(request, response);
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
    }
    
    private boolean isStudent(HttpServletRequest request, HttpServletResponse response) {
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (loggedInUser == null || !loggedInUser.getRoles().contains("student")) {
            return false;
        } else {
            return true;
        }
    }
}
