/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author im757299
 */
public class Attempt implements Serializable {
    private int attemptID;
    private int studentID;
    private double attemptScore;
    private LocalDateTime attemptDate;
    private List<Question> incorrectQuestions;
    private List<Question> correctQuestions;

    public Attempt(int attemptID, int studentID, double attemptScore, LocalDateTime attemptDate, List<Question> incorrectQuestions, List<Question> correctQuestions) {
        this.attemptID = attemptID;
        this.studentID = studentID;
        this.attemptScore = attemptScore;
        this.attemptDate = attemptDate;
        this.incorrectQuestions = incorrectQuestions;
        this.correctQuestions = correctQuestions;
    }

    public int getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(int attemptID) {
        this.attemptID = attemptID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public double getAttemptScore() {
        return attemptScore;
    }

    public void setAttemptScore(double attemptScore) {
        this.attemptScore = attemptScore;
    }
    
    public void calculateScore() {
        this.attemptScore = this.correctQuestions.size() /
                (this.correctQuestions.size() + this.incorrectQuestions.size());
    }

    public LocalDateTime getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(LocalDateTime attemptDate) {
        this.attemptDate = attemptDate;
    }

    public List<Question> getIncorrectQuestions() {
        return incorrectQuestions;
    }

    public void setIncorrectQuestions(List<Question> incorrectQuestions) {
        this.incorrectQuestions = incorrectQuestions;
    }

    public List<Question> getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(List<Question> correctQuestions) {
        this.correctQuestions = correctQuestions;
    }
    
    
}
