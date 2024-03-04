/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mh749813
 */
public class QuestionPool {
    private int ID;
    
    private String name;
    
    private List<Question> poolQuestions;
    
    public QuestionPool(String name){
        this.name = name;
        this.poolQuestions = new ArrayList();
        this.ID = -1;
    }
    
    public QuestionPool(String name, ArrayList<Question> pool){
        this.name = name;
        this.poolQuestions = pool;
        this.ID = -1;
    }
    
    public QuestionPool(int ID, String name, ArrayList<Question> pool){
        this.name = name;
        this.poolQuestions = pool;
        this.ID = ID;
    }
    
    
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
    
    public List<Question.questionType> getQTypes(){
        List<Question> questions = this.poolQuestions;
        
        List<Question.questionType> questionTypes = new ArrayList<>();
        
        for (Question question : questions){
            questionTypes.add(question.getqType());
        }
        
        return questionTypes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
