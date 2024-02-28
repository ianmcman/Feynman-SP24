/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Question;
import business.QuestionPool;
import business.User;
import data.FeynmanDB;
import business.Assessment;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

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
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Public");
            return;
        }
        
        if (!user.getRoles().contains("teacher")) {
            response.sendRedirect("Public");
            return;
        }
        
        String url = "/Teacher/index.jsp";
        String action = request.getParameter("action");
        ArrayList<String> errors = new ArrayList();
        String message = "";
        ArrayList<String> qTypes = new ArrayList(EnumSet.allOf(Question.questionType.class));
        String rPage = request.getParameter("rPage");
        String rIndex = request.getParameter("rIndex");
                
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "qPHome":
                
                url = "/Teacher/qPoolIndex.jsp";
                //List<QuestionPool> qPools = FeynmanDB.getQuestionPools(loggedInUser.getUserID());
                //request.setAttribute("pools",qPools);
                break;
            case "createQuizHome":
                url = "/Teacher/createQuiz.jsp";
                break;
            case "createQuiz":
                url = "/Teacher/createQuiz.jsp";
                try {
                    List<QuestionPool> pools = FeynmanDB.getQuestionPools(loggedInuser.getUsername());
                    request.setAttribute("pools", pools);
                } catch (Exception e) {
                   errors.add("Error receiving pools from database");
                }
                
                List<Assessment.assessmentType> aTypes = new ArrayList<>(Arrays.asList(Assessment.assessmentType.values()));
                request.setAttribute("aTypes", aTypes);
                boolean isValid = true;
                int retakes;
                String retakesString = request.getParameter("retakes");
                String assessmentType = request.getParameter("aType");
                
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
                
                if (assessmentType == null || assessmentType.isEmpty()) {
                    errors.add("Assessment type must be chosen.");
                    isValid = false;
                }
                
                if (isValid) {
                    message = "Quiz creation successful \n"+retakesString+"\n"+assessmentType;
                    
                } else {
                    message = "Quiz creation unsuccessful";
                }
                
                break;
            case "addQ":
                url = "/Teacher/createQuestion.jsp";
                request.setAttribute("rPage",rPage);
                request.setAttribute("rIndex",rIndex);
                request.setAttribute("qTypes",qTypes);
                break;
            case "createQ":
                String qText = request.getParameter("qText");
                String qAnswer = request.getParameter("qAnswer");
                String rawqDiff = request.getParameter("qDiff");
                int qDiff;
                String rawqType = request.getParameter("qType");
                Question.questionType qType;
                if(qText.isBlank()){errors.add("Question text can't be blank.");}
                if(qAnswer.isBlank()){errors.add("Answer can't be blank.");}
                if(rawqDiff.isBlank()){errors.add("Difficulty can't be blank.");}
                else {
                    try {
                        qDiff = Integer.parseInt(rawqDiff);
                        if(1>qDiff||5<qDiff){throw new Exception();}
                    }catch(Exception e){
                        errors.add("Difficulty must be a number between 1 and 5 inclusive.");
                    }
                }
                try{
                    qType = Question.questionType.valueOf(rawqType);
                }catch (IllegalArgumentException e){
                    errors.add("Question type can't be custom.");
                    rawqType="";
                }
                if(!errors.isEmpty()){
                    //return to question form
                    request.setAttribute("qText",qText);
                    request.setAttribute("qAnswer",qAnswer);
                    request.setAttribute("qDiff",rawqDiff);
                    request.setAttribute("qType",rawqType);
                    request.setAttribute("qTypes",qTypes);
                    url = "/Teacher/createQuestion.jsp";
                    request.setAttribute("rPage",rPage);
                    request.setAttribute("rIndex",rIndex);
                }else{
                    //add question to DB
                switch(rPage){
                    case "index":
                    default:
                        url = "/Teacher/index.jsp";
                }
                }
                break;
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
