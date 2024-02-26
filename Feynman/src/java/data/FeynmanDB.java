/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Attempt;
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

/**
 *
 * @author mcman
 */
public class FeynmanDB {
    private static final Logger LOG = Logger.getLogger(FeynmanDB.class.getName());

    public static User authenticateCredentials(String username, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
        String query = "SELECT UserID, FirstName, LastName, RoleName FROM user "
                     + "JOIN userroles ON user.userid = userroles.userid "
                     + "JOIN roles ON userroles.roleid = roles.roleid "
                     + "WHERE username = ? AND password = ?";
 
        ps = connection.prepareStatement(query);
        ps.setString(1, username);            
        ps.setString(1, password);
        rs = ps.executeQuery();

        if (rs.next()) {
            user = new User(username, password);
            ArrayList<String> roles = new ArrayList<String>();
            user.setFullName(rs.getString("FirstName"), 
                             rs.getString("LastName"));
            user.setUserID(rs.getInt("userID"));
            while(rs.next()){
                roles.add(rs.getString("RoleName"));
            }
            user.setRoles(roles);
        }
        
        connection.close();
        pool.freeConnection(connection);
        
        return user;

        }   
    
    public static List<QuestionPool> getQuestionPools(int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
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
                return null; // need to work on this
            }
        } catch (SQLException e) {
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
    }
}

    
    
    
    

