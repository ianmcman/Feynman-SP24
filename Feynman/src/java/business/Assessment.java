/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;

/**
 *
 * @author im757299
 */
public class Assessment implements Serializable{
    public int assessmentID;
    public String assessmentName;
    public boolean isRandom;
    public int retakes;
    public int length;
    public int poolID;
    public enum assessmentType {
        DRILL,
        PLACEMENT,
        EXAM;
    }
    private assessmentType aType;

    public Assessment() {}

    public Assessment(int assessmentID, String assessmentName, int retakes, int poolID, assessmentType aType) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.retakes = retakes;
        this.poolID = poolID;
        this.aType = aType;
    }
    
    public Assessment(int assessmentID, String assessmentName, boolean isRandom, int retakes, int length, int poolID, assessmentType aType) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.isRandom = isRandom;
        this.retakes = retakes;
        this.length = length;
        this.poolID = poolID;
        this.aType = aType;
    }

    public Assessment(String assessmentName, int retakes, int poolID, assessmentType aType) {
        this.assessmentName = assessmentName;
        this.retakes = retakes;
        this.poolID = poolID;
        this.aType = aType;
    }
    
    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public boolean isIsRandom() {
        return isRandom;
    }

    public void setIsRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }

    public int getRetakes() {
        return retakes;
    }

    public void setRetakes(int retakes) {
        this.retakes = retakes;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public assessmentType getaType() {
        return aType;
    }

    public void setaType(assessmentType aType) {
        this.aType = aType;
    }   

    public int getPoolID() {
        return poolID;
    }

    public void setPoolID(int poolID) {
        this.poolID = poolID;
    }
    
    
    
}


