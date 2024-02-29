/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ms461431
 */
public class Teacher extends User implements Serializable {
    private List<Assessment> assessments;
    private List<QuestionPool> qPools;
    
    public Teacher() {}
    
    public Teacher (List<Assessment> tests, List<QuestionPool> pools, int UID, String username, String password, ArrayList<String> roles, String firstName, String lastName){
        super(UID, username, password, roles, firstName, lastName);
        this.assessments = tests;
        this.qPools = pools;
    }
    
    public List<Assessment> getAssessments() {
        return assessments;
    }
    public void setAssessments(List<Assessment> tests){
        this.assessments = tests;
    }
    public void addAssessment(Assessment test){
        this.assessments.add(test);
    }
    public List<QuestionPool> getQuestionPool(){
        return qPools;
    }
    public void addQuestionPool(QuestionPool pool){
        this.qPools.add(pool);
    }
    public void setQuestionPools(List<QuestionPool> pools){
        this.qPools = pools;
    }
    public void removeAssessment(Assessment test){
        this.assessments.remove(test);
    }
    public void removeQuestionPool(QuestionPool pool){
        this.qPools.remove(pool);
    }
}
