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

public class FeynmanDB {
    private static final Logger LOG = Logger.getLogger(FeynmanDB.class.getName());

    public static User authenticateCredentials(String username, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        User user = null;
        
        String query = "SELECT UserID, FirstName, LastName, RoleName FROM user "
                     + "JOIN userroles ON user.userid = userroles.userid "
                     + "JOIN roles ON userroles.roleid = roles.roleid "
                     + "WHERE username = ? AND password = ?";
 
        PreparedStatement ps = connection.prepareStatement(query);
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
        
        connection.close();
        pool.freeConnection(connection);
        
        return user;

    }   

    public static int registerUser(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query
                = "INSERT INTO user (FirstName, LastName, Username, Password) "
                + "VALUES (?, ?, ?, ?)";
        
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(3, user.getFirstName());
        ps.setString(3, user.getLastName());
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        
        connection.close();
        pool.freeConnection(connection);

        return ps.executeUpdate();
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
        
        String query = "SELECT * FROM assessmentattempts as aa" + 
                "JOIN assessmentattemptquestions as aaq ON aa.attemptID=aaq.attemptID" +
                "WHERE userID = ? GROUP BY aaq.attemptID";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()){
                return null; // need to work on this
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
