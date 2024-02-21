/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.QuestionPool;
import business.User;
import java.sql.*;
import java.time.Year;
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

    public static boolean authenticateCredentials(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static User getUser(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
}
