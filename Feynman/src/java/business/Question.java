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
    
    //private enum<questionType> questionType;
    
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
    
//    public boolean checkAnswer(){
//    
//    }
    
//    public boolean compareQuestion(){
//        
//    }
    
    public int getDifficulty(){
        return this.difficulty;
    }
    
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
    
    //still need to add getQType() and setQType
    
}
