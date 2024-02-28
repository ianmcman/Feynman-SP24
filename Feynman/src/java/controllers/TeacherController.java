/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

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

/**
 *
 * @author im757299
 */
public class TeacherController extends HttpServlet {

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
//        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            response.sendRedirect("Public");
//            return;
//        }
        
        String url = "/Teacher/index.jsp";
        String action = request.getParameter("action");
        ArrayList<String> errors = new ArrayList();
        String message = "";

        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "qPHome":
                // request.setAttribute("loggedInUser", loggedInUser);
                url = "/Teacher/qPoolIndex.jsp";
                break;
            case "createQuizHome":
                // request.setAttribute("loggedInUser", loggedInUser);
                url = "/Teacher/createQuiz.jsp";
                break;
            case "createQuiz":
                url = "/Teacher/createQuiz.jsp";
                // try {
                    // List<QuestionPool> pools = FeynmanDB.getQuestionPools(loggedInuser.getUsername());
                    // request.setAttribute("pools", pools);
               // } catch (SQLException e) {
                   // errors.add("Error receiving pools from database");
               // }
                boolean isValid = true;
                int retakes;
                String retakesString = request.getParameter("retakes");
                if (retakesString == null || retakesString.isEmpty()) {
                    errors.add("Retakes must not be null or empty.");
                    isValid = false;
                } else {
                    retakes = Integer.parseInt(retakesString);
                    if (retakes < 0) {
                        errors.add("Retakes must not be less than 0");
                        isValid = false;
                    }
                }
                
                if (isValid) {
                    message = "Quiz creation successful";
                } else {
                    message = "Quiz creation unsuccessful";
                }
                
                break;
            case "addQuestion":
                // request.setAttribute("loggedInUser", loggedInUser);
                url = "/Teacher/createQuestion.jsp";
                break;
            case "default":
                // request.setAttribute("loggedInUser", loggedInUser);
                url = "/Teacher/index.jsp";
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

}
