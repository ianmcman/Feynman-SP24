/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

/**
 *
 * @author mh749813
 */
public class Question {
    private int questionID;
    
    private String formula;
    
    private String answer;
    
    private enum questionType{
        ADDITION,
        SUBTRACTION,
        DIVISION,
        MULTIPLICATION
    }
    
    private int difficulty;
    
    public String getQuestionText(){
        return this.formula;
    }
    
    public void setQuestionText(String formula){
        this.formula = formula;
    }
    
    public String getAnswer(){
        return this.answer;
    }
    
    public void setAnswer(String answer){
        this.answer = answer;
    }
    
    
    public boolean checkAnswer(String userAnswer){
        return this.answer.equalsIgnoreCase(userAnswer);
    }
    
    public boolean compareQuestion(Question question){
        return question.formula.equalsIgnoreCase(this.formula);  
    }
    
    public int getDifficulty(){
        return this.difficulty;
    }
    
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
    
    public String getQType(){
        
    }
    
    public void setQType(){
        
    }
    
}
