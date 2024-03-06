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
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

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
                ArrayList<QuestionPool> qPools = new ArrayList<>(FeynmanDB.getQuestionPoolNames(user.getUserID()));
                request.setAttribute("pools", qPools);
                break;
            case "createQuizHome":
                url = "/Teacher/createQuiz.jsp";
                List<QuestionPool> pools = null;
                try {
                    pools = FeynmanDB.getQuestionPoolNames(user.getUserID());
                } catch (Exception e) {
                    errors.add("Error receiving pools from database");
                }
                request.setAttribute("pools", pools);
                List<Assessment.assessmentType> aTypes = new ArrayList<>(Arrays.asList(Assessment.assessmentType.values()));
                request.setAttribute("aTypes", aTypes);
                break;
            case "createQuiz":
                String aName = request.getParameter("name");
                String retakesString = request.getParameter("retakes");
                int retakes = -1;
                String infRetakes = request.getParameter("infiniteRetakes");
                String pChoiceString = request.getParameter("poolChoice");
                int pChoice = -1;
                String aTypeString = request.getParameter("aType");
                Assessment a = null;
                Assessment.assessmentType aType = null;
                String randomString = request.getParameter("random");
                boolean random = false;
                String lengthString = request.getParameter("length");
                int length = -1;

                if (aName == null || aName.isBlank()) {
                    errors.add("Assessment name can't be blank or null.");
                }

                if (infRetakes != null) {
                    if (infRetakes.equalsIgnoreCase("on")) {
                        retakes = 10000;
                    }
                }

                if (infRetakes == null) {
                    if (retakesString == null || retakesString.isBlank()) {
                        errors.add("Retakes must not be null.");
                    } else {
                        try {
                            retakes = Integer.parseInt(retakesString);
                        } catch (NumberFormatException e) {
                            errors.add("Retakes must be a number");
                        }
                    }
                }

                if (randomString != null) {
                    if (randomString.equalsIgnoreCase("on")) {
                        random = true;
                    }
                    if (lengthString == null || lengthString.isBlank()) {
                        errors.add("Length must not be null.");
                    } else {
                        try {
                            length = Integer.parseInt(lengthString);
                        } catch (NumberFormatException e) {
                            errors.add("Length must be a number.");
                        }
                    }
                }

                if (length < 1) {
                    errors.add("Length must be 1 or greater.");
                }

                if (retakes < 0) {
                    errors.add("Retakes must be 0 or greater.");
                }

                try {
                    pChoice = Integer.parseInt(pChoiceString);
                } catch (NumberFormatException e) {
                    errors.add("Pool choice must have a numeric ID.");
                }

                if (FeynmanDB.getQuestionPool(pChoice, user.getUserID()) == null) {
                    errors.add("Invalid question pool ID.");
                }

                try {
                    aType = Assessment.assessmentType.valueOf(aTypeString);
                } catch (IllegalArgumentException e) {
                    errors.add("Assessment type can't be custom.");
                    aTypeString = "";
                }

                if (errors.isEmpty()) {
                    // add assessment to DB
                    a = new Assessment();
                    a.setAssessmentName(aName);
                    a.setPoolID(pChoice);
                    a.setRetakes(retakes);
                    a.setaType(aType);
                    a.setIsRandom(random);
                    a.setLength(length);
                    int id = FeynmanDB.addAssessment(a);
                    if (id == -1) {
                        errors.add("There was a problem adding the assessment to the database.");
                    } else {
                        a.setAssessmentID(id);
                    }
                    message += "Assessment Creation Success!";
                }

                if (!(errors.isEmpty())) {
                    url = "/Teacher/createQuiz.jsp";
                    request.setAttribute("name", aName);
                    request.setAttribute("retakes", retakesString);
                    

                }

                break;
            case "addQuestion":
                url = "/Teacher/createQuestion.jsp";
                request.setAttribute("rPage", rPage);
                request.setAttribute("rIndex", rIndex);
                request.setAttribute("qTypes", qTypes);
                break;
            case "addQPool":
                url = "/Teacher/createQPool.jsp";
                request.setAttribute("rPage", rPage);
                request.setAttribute("rIndex", rIndex);
                break;
            case "editQPool":
                url = "/Teacher/editQPool.jsp";
                request.setAttribute("rPage", rPage);
                request.setAttribute("rIndex", rIndex);
                String edit = request.getParameter("edit");
                if (null == edit) {
                    edit = "";
                }
                if (!rIndex.isBlank()) {
                    int ID = Integer.parseInt(rIndex);
                    QuestionPool qp = FeynmanDB.getQuestionPool(ID, user.getUserID());
                    switch (edit) {
                        case "rename":
                            //Rename Question Pool
                            String name = request.getParameter("qPName");
                            if (name.isBlank()) {
                                errors.add("Question Pool must have a name");
                            } else {
                                qp.setName(name);
                                int e = FeynmanDB.renameQuestionPool(qp.getID(), name);
                                if (e < 1) {
                                    errors.add("Question Pool failed to rename");
                                }
                            }
                            break;
                        case "removeQ":
                            //Remove Question
                            int QID = Integer.parseInt(request.getParameter("QID"));
                            FeynmanDB.removeQuestionFromPool(QID, qp.getID());
                            qp = FeynmanDB.getQuestionPool(qp.getID());
                            break;
                    }

                    request.setAttribute("qp", qp);
                }
                break;
            case "createQuestion":
                String qText = request.getParameter("qText");
                String qAnswer = request.getParameter("qAnswer");
                String rawqDiff = request.getParameter("qDiff");
                Question q = null;
                int qDiff = 0;
                String rawqType = request.getParameter("qType");
                Question.questionType qType = null;
                if (qText.isBlank()) {
                    errors.add("Question text can't be blank.");
                }
                if (qAnswer.isBlank()) {
                    errors.add("Answer can't be blank.");
                }
                if (rawqDiff.isBlank()) {
                    errors.add("Difficulty can't be blank.");
                } else {
                    try {
                        qDiff = Integer.parseInt(rawqDiff);
                        if (1 > qDiff || 5 < qDiff) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        errors.add("Difficulty must be a number between 1 and 5 inclusive.");
                    }
                }
                try {
                    qType = Question.questionType.valueOf(rawqType);
                } catch (IllegalArgumentException e) {
                    errors.add("Question type can't be custom.");
                    rawqType = "";
                }
                if (errors.isEmpty()) {
                    //add question to DB
                    q = new Question(-1, qText, qAnswer, qType, qDiff);
                    int id = FeynmanDB.addQuestion(q);
                    if (id == -1) {
                        errors.add("There was a pro`blem with adding the question to the database.");
                    } else {
                        q.setID(id);
                    }
                }
                if (!(errors.isEmpty())) {
                    //return to question form
                    request.setAttribute("qText", qText);
                    request.setAttribute("qAnswer", qAnswer);
                    request.setAttribute("qDiff", rawqDiff);
                    request.setAttribute("qType", rawqType);
                    request.setAttribute("qTypes", qTypes);
                    url = "/Teacher/createQuestion.jsp";
                    request.setAttribute("rPage", rPage);
                    request.setAttribute("rIndex", rIndex);
                } else {
                    switch (rPage) {
                        case "editQP":
                            url = "/Teacher/editQPool.jsp";
                            request.setAttribute("rPage", rPage);
                            request.setAttribute("rIndex", rIndex);
                            int qpID = Integer.parseInt(rIndex);
                            if (q != null && q.getID() != -1) {
                                FeynmanDB.addQuestionToPool(q.getID(), qpID);
                            }
                            QuestionPool qp = FeynmanDB.getQuestionPool(qpID, user.getUserID());
                            request.setAttribute("qp", qp);

                            break;
                        case "index":
                        default:
                            url = "/Teacher/index.jsp";
                    }
                }
                break;
            case "createQPool":
                String qPName = request.getParameter("qPName");
                if (qPName.isBlank()) {
                    errors.add("Question Pool Name can't be blank.");
                }
                if (errors.isEmpty()) {
                    //Add Question Pool to DB
                    QuestionPool pool = new QuestionPool(qPName);
                    int id = FeynmanDB.addQuestionPool(pool, user.getUserID());
                    if (id == -1) {
                        errors.add("There was a problem with adding the question pool to the database.");
                    } else {
                        pool.setID(id);
                    }
                }
                if (!errors.isEmpty()) {
                    //Return to add Question Pool
                    url = "/Teacher/createQPool.jsp";
                    request.setAttribute("rPage", rPage);
                    request.setAttribute("rIndex", rIndex);
                    request.setAttribute("qPName", qPName);
                } else {
                    switch (rPage) {
                        case "index":
                        default:
                            url = "/Teacher/qPoolIndex.jsp";
                            qPools = new ArrayList<>(FeynmanDB.getQuestionPoolNames(user.getUserID()));
                            request.setAttribute("pools", qPools);
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
