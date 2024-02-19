/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author im757299
 */
public class Classroom implements Serializable {
    private int classroomID;
    private String classroomName;
    private String classroomDescription;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Assessment> classroomAssessments;

    public Classroom(int classroomID, String classroomName, String classroomDescription, List<Student> students, List<Teacher> teachers, List<Assessment> classroomAssessments) {
        this.classroomID = classroomID;
        this.classroomName = classroomName;
        this.classroomDescription = classroomDescription;
        this.students = students;
        this.teachers = teachers;
        this.classroomAssessments = classroomAssessments;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(int classroomID) {
        this.classroomID = classroomID;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getClassroomDescription() {
        return classroomDescription;
    }

    public void setClassroomDescription(String classroomDescription) {
        this.classroomDescription = classroomDescription;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }
    
    public void removeStudent(Student student) {
        this.students.remove(student);
    }
    
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Assessment> getClassroomAssessments() {
        return classroomAssessments;
    }

    public void setClassroomAssessments(List<Assessment> classroomAssessments) {
        this.classroomAssessments = classroomAssessments;
    }
    
    public void addAssessment(Assessment assessment) {
        this.classroomAssessments.add(assessment);
    }
    
    public void removeAssessment(Assessment assessment) {
        this.classroomAssessments.remove(assessment);
    }
    
    
}
