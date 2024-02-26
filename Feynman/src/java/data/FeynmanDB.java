/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Attempt;
import business.Question;
import business.QuestionPool;
import business.Student;
import business.User;
import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeynmanDB {
    private static final Logger LOG = Logger.getLogger(FeynmanDB.class.getName());

    public static User authenticateCredentials(String username, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        User user = null;
        
        String query = "SELECT UserID, FirstName, LastName, RoleName FROM user "
                     + "JOIN userroles ON user.userid = userroles.userid "
                     + "JOIN roles ON userroles.roleid = roles.roleid "
                     + "WHERE username = ? AND password = ?";
 
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);            
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new User(username, password);
                ArrayList<String> roles = new ArrayList<>();
                user.setFullName(rs.getString("FirstName"), 
                                 rs.getString("LastName"));
                user.setUserID(rs.getInt("userID"));
                roles.add(rs.getString("RoleName"));
                while(rs.next()){
                    roles.add(rs.getString("RoleName"));
                }
                user.setRoles(roles);
            }
        }
        
        return user;

    }   

    public static int registerUser(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();

        String query
                = "INSERT INTO user (FirstName, LastName, Username, Password) "
                + "VALUES (?, ?, ?, ?)";
        
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());



            return ps.executeUpdate();
        
        }
}

    
    public static List<QuestionPool> getQuestionPools(int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<QuestionPool> qPools = new ArrayList();
        QuestionPool qPool = null;
        
        String query = "SELECT * FROM pool as p "
                + "JOIN userpools as up ON p.PoolID=up.PoolID "
                + "JOIN questionpools as qp ON p.PoolID=qp.PoolID "
                + "JOIN question as q ON qp.QID=q.QID "
                + "WHERE UserID = ? ORDER BY PoolID ASC";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()){
                // Initializes the Question Pool, or adds it to the list and resets the Pool
                String pName = rs.getString("PoolName");
                if(null == qPool){
                    qPool.setName(pName);
                }else if(qPool.getName() != pName){
                    qPools.add(qPool);
                    qPool = null;
                    qPool.setName(pName);
                }
                // Loads a Question into the Question Pool
                int qID = rs.getInt("q.QID");
                String qForm = rs.getString("QFormula");
                String qAnswer = rs.getString("QAnswer");
                String qDif = rs.getString("QDifficulty");
                Boolean qMult = rs.getBoolean("QInclMult");
                Boolean qDiv = rs.getBoolean("QInclDiv");
                Boolean qAdd = rs.getBoolean("QInclAdd");
                Boolean qSub = rs.getBoolean("QInclSub");
                int qDifficulty = Integer.parseInt(qDif);
                Question.questionType qType=null;
                if (qDiv) {qType = Question.questionType.DIVISION;}
                else if(qMult){qType = Question.questionType.MULTIPLICATION;}
                else if(qSub) {qType = Question.questionType.SUBTRACTION;}
                else if(qAdd) {qType = Question.questionType.ADDITION;}
                Question q = new Question(qID, qForm, qAnswer, qType, qDifficulty);
                qPool.addQuestion(q);
            }
            return qPools;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getQuestionPools", e);
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getQuestionPools", e);
                System.out.println(e);
            }
        }
    }
    
    public static void addQuestionPool(QuestionPool qPool, int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String queryPoolAdd = "INSERT INTO pool " +
                            "(PoolName) VALUES (?)";
        String queryGetPoolID = "SELECT PoolID FROM pool " +
                                "WHERE PoolName = ?";
        String queryLinkUserPool = "INSERT INTO userpools " +
                                    "(UserID, PoolID) VALUES (?, ?)";
        String queryLinkQuestionPool = "INSERT INTO questionpools " +
                                        "(QID, PoolID) VALUES (?, ?)";
        int poolID;
        try{
            ps = connection.prepareStatement(queryPoolAdd);
            ps.setString(1, qPool.getName());
            int update = ps.executeUpdate();
            if(1 > update){ throw new SQLException();}
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionPool adding Question Pool", e);
            System.out.println(e);
            return;
        } 
        try{
            ps = connection.prepareStatement(queryGetPoolID);
            ps.setString(1, qPool.getName());
            rs = ps.executeQuery();
            poolID = rs.getInt("PoolID");
        }catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionPool getting Question Pool ID", e);
            System.out.println(e);
            return;
        } 
        try{
            ps = connection.prepareStatement(queryLinkUserPool);
            ps.setInt(1, userID);
            ps.setInt(2, poolID);
            int update = ps.executeUpdate();
            if(1 > update){ throw new SQLException();}
        }catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionPool Linking User to Question Pool", e);
            System.out.println(e);
            return;
        } 
        try{
            List<Question> questions = qPool.getQuestions();
            ps = connection.prepareStatement(queryLinkQuestionPool);
            for (Question q : questions){
                ps.setInt(1, q.getID());
                ps.setInt(2, poolID);
                int update = ps.executeUpdate();
                if(1 > update){ throw new SQLException();}
            }
        }catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionPool Linking Questions to Question Pool", e);
            System.out.println(e);
            return;
        } 
        finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getQuestionPools", e);
                System.out.println(e);
            }
        }
    }
    
    public static List<Attempt> getStudentAttempts(int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Attempt attempt = null;
        
        String query = ""; //need to do query
        
        List<Attempt> studentAttempts = new ArrayList<>();
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()){
                //need to get: attemptID, studentID, attemptScore, attemptdate, inccorectQuestions, correctQuestions set to Attempt class; then added to list
                attempt.setStudentID(rs.getInt("userID"));
            }
            
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** select all null pointer?", e);
            }
        }
        return null;
    }
    
    public static boolean nameExists(String name) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT Username FROM user "
                + "WHERE name = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** name check null pointer", e);
                throw e;
            }
        }
    }
}

    
    
    
    

