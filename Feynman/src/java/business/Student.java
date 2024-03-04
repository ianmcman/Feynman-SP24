/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ms461431
 */
public class Student extends User implements Serializable {
    private List<Assessment> assessments;
    private List<Attempt> assessmentGrades;
    
    public Student(){}
    
    public Student (List<Assessment> tests, List<Attempt> grades, int UID, String username, String password, Integer roleID, ArrayList<String> roles, String firstName, String lastName){
        //super(UID, username, password, roleID, roles, firstName, lastName);
        this.assessments = tests;
        this.assessmentGrades = grades;
    }
    public List<Assessment> getAssessments() {
        return assessments;
    }
    public void setAssessments(List<Assessment> tests){
        this.assessments = tests;
    }
    public List<Attempt> getAssessmentGrades(int aID){
        List<Attempt> grades = new ArrayList();
        for (Attempt a : this.assessmentGrades) {
            if (a.getAttemptID() == aID){
                grades.add(a);
            }
        }
        return grades;
    }
    public List<Attempt> getGrades(){
        return assessmentGrades;
    }
    public void setGrades(List<Attempt> grades){
        this.assessmentGrades = grades;
    }
    public void addGrade(Attempt grade){
        this.assessmentGrades.add(grade);
    }
}
