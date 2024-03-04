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
import static data.FeynmanDB.getQuestionPool;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
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
        ArrayList<String> assessmentTypes = new ArrayList(EnumSet.allOf(Assessment.assessmentType.class));
        List<QuestionPool> pools = null;
        int retakes;
        try {
            pools = FeynmanDB.getQuestionPools(user.getUserID());
        } catch (SQLException e) {
            errors.add("SQL Exception retrieving pools.");
        }
        
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
                request.setAttribute("pools", pools);
                request.setAttribute("aTypes", assessmentTypes);

                break;
            case "createQuiz":
                url = "/Teacher/createQuiz.jsp";
                
                boolean isValid = true;
                retakes = 0;
                String retakesString = request.getParameter("retakes");
                String infiniteRetakesString = request.getParameter("infiniteRetakes");
                String poolChoiceString = request.getParameter("poolchoice");
                int poolChoice;
                String name = request.getParameter("name");
                String rawAType = request.getParameter("aType");
                Assessment.assessmentType aType = null;
                boolean infiniteRetakes = false;
                System.err.println(retakesString);
                
                if (name == null || name.isBlank()) {
                    errors.add("Name must not be null or empty.");
                    isValid = false;
                }
                
                if (retakesString == null) {
                    if (infiniteRetakesString == null) {
                        infiniteRetakes = false;
                    } else if (infiniteRetakesString.equalsIgnoreCase("on")) {
                        infiniteRetakes = true;
                        retakes = 1000;
                    } else {
                        errors.add("Retakes must not be null or empty.");
                        isValid = false;
                    }
                } else {
                    retakes = Integer.parseInt(retakesString);
                    if (retakes < 0) {
                        errors.add("Retakes must not be less than 0");
                        isValid = false;
                    }
                }
                
                if (poolChoiceString == null || poolChoiceString.isBlank()) {
                    errors.add("Pool choice must not be null or empty.");
                    isValid = false;
                } else {
                    try {
                        poolChoice = Integer.parseInt(poolChoiceString);
                        if (getQuestionPool(poolChoice, user.getUserID()) == null) {
                            errors.add("That pool does not exist.");
                        }
                    } catch (NumberFormatException e) {
                        errors.add("Pool choice must be a number.");
                }
                
                try {
                    aType = Assessment.assessmentType.valueOf(rawAType);
                } catch (IllegalArgumentException e) {
                    errors.add("Assessment type can't be custom.");
                    rawAType = "";
                }
                
                if (!isValid) {
                    message = "Quiz creation unsuccessful";
                    request.setAttribute("retakes", retakesString);
                    request.setAttribute("infiniteRetakes", infiniteRetakesString);
                    request.setAttribute("poolChoice", pools);
                    request.setAttribute("aTypes", assessmentTypes);
                    request.setAttribute("name", name);
                    request.setAttribute("aType", rawAType);
                } // else {
                   // Assessment a = new Assessment(name, retakes, aType, getQuestionPool(poolChoice, user.getUserID()));
                    
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
                    Question q = new Question(-1,qText,qAnswer,qType,qDiff);
                    int id = FeynmanDB.addQuestion(q);
                    if(id == -1){errors.add("There was a problem with adding the question to the database.");}
                    else{q.setID(id);}
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
