/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.util.List;

/**
 *
 * @author mh749813
 */
public class QuestionPool {
    private int ID;
    
    private String name;
    
    private List<Question> poolQuestions;
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public List<Question> getQuestions(){
        return this.poolQuestions;
    }
    
    public void setQuestions(List<Question> questions){
        this.poolQuestions = questions;
    }
    
    public void addQuestion(Question question){
        this.poolQuestions.add(question);
    }
    
    public void removeQuestion(Question question){
        this.poolQuestions.remove(question);
    }
    
    public float getAvgDifficulty(){
        List<Question> questions = this.poolQuestions;
        
        int numQuestions = 0;
        int totalDiff = 0;
        
        for (Question question : questions){
            totalDiff += question.getDifficulty();
            numQuestions++;
        }
        
        return totalDiff / numQuestions;
    }
    
    public int getMaxDifficulty(){
        List<Question> questions = this.poolQuestions;
        
        int max = 0;      
        
        for (Question question : questions){
            if(question.getDifficulty() > max){
                max = question.getDifficulty();
            }
        }
        
        return max;
    }
    
    //need to add getQTypes()
}
