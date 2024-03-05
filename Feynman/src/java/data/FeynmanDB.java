/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Assessment;
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
        
        String query = "SELECT * FROM user \n" +
                       "LEFT JOIN userroles \n" +
                       "	ON user.UserID = userroles.UserID \n" +
                       "LEFT JOIN roles \n" +
                       "	on userroles.RoleID = roles.RoleID \n" +
                       "WHERE Username = ? AND Password = ?";
        
 
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);            
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(username, password);
                    ArrayList<String> roles = new ArrayList<>();
                    user.setFullName(rs.getString("FirstName"), 
                                     rs.getString("LastName"));
                    user.setUserID(rs.getInt("userID"));
                    do {
                        roles.add(rs.getString("RoleName"));
                    } while(rs.next());
                    user.setRoles(roles);
                }
            }
        }
        
        return user;

    }   
    
    public static void registerUser(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();

        String query
                = "INSERT INTO user (FirstName, LastName, Username, Password) "
                + "VALUES (?, ?, ?, ?)";
        String query2
                = "INSERT INTO userroles (UserID, RoleID) "
                + "VALUES (?, ?)";
        
        try (Connection connection = pool.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement ps2 = connection.prepareStatement(query2)) {
            
            // Start transaction
            connection.setAutoCommit(false);
            
            // Insert the user
            ps1.setString(1, user.getFirstName());
            ps1.setString(2, user.getLastName());
            ps1.setString(3, user.getUsername());
            ps1.setString(4, user.getPassword());
            int affectedRows = ps1.executeUpdate();
            
            if  (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            int userID;
            try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
                
   
            // Insert each user role
            for (String role : user.getRoles()) {
                if (role.equals("parent")) {
                    ps2.setInt(2, 4);
                } else if (role.equals("student")) {
                    ps2.setInt(2, 3);
                }
                ps2.setInt(1, userID);
                ps2.executeUpdate();
            }
   
            
            // Commit transaction
            connection.commit();
        }

    }
    

    public static ArrayList<User> getAllUsers() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ArrayList<User> users = new ArrayList<>();
        
        String query1 = "SELECT * FROM user \n";
        String query2 = """
                        SELECT RoleName FROM userroles 
                        JOIN roles 
                        ON userroles.RoleID = roles.RoleID 
                        WHERE UserID = ?""";
        
 
        try (Connection connection = pool.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query1)) {
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String username = rs.getString("Username");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                ArrayList<String> roles = new ArrayList<>();
                
                try (PreparedStatement ps = connection.prepareStatement(query2)) {
                    ps.setInt(1, userID);
                    try (ResultSet rolesRs = ps.executeQuery()) {
                        while (rolesRs.next()) {
                            roles.add(rolesRs.getString("RoleName"));
                        }
                    }
                }
                
                User user = new User(userID, username, roles, firstName, lastName);
                users.add(user); 
            }
        }
        
        return users;

    }   
    
    public static User getUser(int userID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        User user = null;
        
        String query1 = "SELECT * FROM user \n WHERE UserID = ?";
        String query2 = """
                        SELECT RoleName FROM userroles 
                        JOIN roles 
                        ON userroles.RoleID = roles.RoleID 
                        WHERE UserID = ?""";
        
 
        try (Connection connection = pool.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(query1)) {
            ps1.setInt(1, userID);
            try (ResultSet userRs = ps1.executeQuery()) {
                if (userRs.next()) {
                    String username = userRs.getString("Username");
                    String firstName = userRs.getString("FirstName");
                    String lastName = userRs.getString("LastName");
                    ArrayList<String> roles = new ArrayList<>();
                    try (PreparedStatement ps2 = connection.prepareStatement(query2)) {
                        ps2.setInt(1, userID);
                        try (ResultSet rolesRs = ps2.executeQuery()) {
                            while (rolesRs.next()) {
                                roles.add(rolesRs.getString("RoleName"));
                            }
                        }
                    }
                    user = new User(userID, username, roles, firstName, lastName);
                }
                
            }
        }
        return user;       
    }    
    
    public static int deleteUser(int userID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        User user = null;
        
        String query = "DELETE FROM user WHERE UserID = ?";
        
 
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            return ps.executeUpdate();
        }     
    }   
    
    // Populates an ArrayList of Question Pools with the Names and ID's for a user
    public static List<QuestionPool> getQuestionPoolNames(int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        List<QuestionPool> qPools = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM pool as p "
                + "JOIN userpools as up ON p.PoolID=up.PoolID "
                + "WHERE UserID = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()){
                qPools.add(new QuestionPool(rs.getInt("PoolID"),rs.getString("PoolName")));
            }
            return qPools;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getQuestionPoolNames", e);
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** Exception: cleaning up getQuestionPoolNames", e);
                System.out.println(e);
            }
        }
    }
    
    // combines the getQuestionPoolNames and the getQuestionsInPool to return the Question Pools for a user with the Questions
    public static List<QuestionPool> getQuestionPoolsFull(int userID){
        List<QuestionPool> qPools = new ArrayList();
        qPools = getQuestionPoolNames(userID);
        for (QuestionPool qp : qPools){
            qp = getQuestionsInPool(qp);
        }
        return qPools;
    }
    
    public static QuestionPool getQuestionsInPool(QuestionPool qp){
        if(qp.getID() != -1){
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            String query = "SELECT * FROM questionpools as qp "
                    + "JOIN question as q ON qp.QID=q.QID "
                    + "WHERE PoolID = ? ";
            try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, qp.getID());
            rs = ps.executeQuery();
            while(rs.next()){
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
                qp.addQuestion(new Question(qID, qForm, qAnswer, qType, qDifficulty));
            }
            } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getQuestionsInPool", e);
            System.out.println(e);
            return qp;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getQuestionsInPool", e);
                System.out.println(e);
            }
        }
        }
        return qp;
    }
    
    public static QuestionPool getQuestionPool(int poolID, int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM userpools " +
                        "WHERE UserID = ? AND PoolID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, poolID);
            rs = ps.executeQuery();
            if(rs.next()){return getQuestionPool(poolID);}
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getQuestionPool User check", e);
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getQuestionPool User check", e);
                System.out.println(e);
            }
        }
        return null;
    }
    
    public static QuestionPool getQuestionPool(int poolID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        QuestionPool qPool = null;
        
        String query = "SELECT * FROM pool as p "
                + "WHERE PoolID = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, poolID);
            rs = ps.executeQuery();
            while(rs.next()){
                qPool = new QuestionPool(poolID, rs.getString("PoolName"));
            }
            qPool = getQuestionsInPool(qPool);
            return qPool;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getQuestionPool", e);
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getQuestionPool", e);
                System.out.println(e);
            }
        }
    }
    
    public static int renameQuestionPool(int ID, String name){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE pool SET PoolName = ? WHERE PoolID = ? ";
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, ID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: renameQuestionPool", e);
            System.out.println(e);
            return -1;
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up renameQuestionPool", e);
                System.out.println(e);
            }
        }
    }
    
    public static int addQuestionPool(QuestionPool qPool, int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String queryPoolAdd = "INSERT INTO pool " +
                            "(PoolName) VALUES (?)";
        String queryGetPoolID = "SELECT * FROM pool " +
                                "WHERE PoolName = ?";
        String queryLinkUserPool = "INSERT INTO userpools " +
                                    "(UserID, PoolID) VALUES (?, ?)";
        String queryLinkQuestionPool = "INSERT INTO questionpools " +
                                        "(QID, PoolID) VALUES (?, ?)";
        int poolID = -1;
        try{
            ps = connection.prepareStatement(queryPoolAdd);
            ps.setString(1, qPool.getName());
            int update = ps.executeUpdate();
            if(1 > update){ throw new SQLException();}
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionPool adding Question Pool", e);
            System.out.println(e);
            return -1;
        } 
        try{
            ps = connection.prepareStatement(queryGetPoolID);
            ps.setString(1, qPool.getName());
            rs = ps.executeQuery();
            while(rs.next()){
                poolID = rs.getInt("PoolID");
            }
        }catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionPool getting Question Pool ID", e);
            System.out.println(e);
            return -1;
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
            if(poolID == -1){
                System.out.println("Pool ID never read.");
                LOG.log(Level.SEVERE, "*** Pool ID never read.", e);
            }
            return -1;
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
            return -1;
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
        return poolID;
    }
    
    public static void addQuestionToPool(int QID, int poolID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO questionpools " +
                        "(QID, PoolID) VALUES (?, ?)";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, QID);
            ps.setInt(2, poolID);
            int update = ps.executeUpdate();
            if(1 > update){ throw new SQLException();}
        }catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestionToPool", e);
            System.out.println(e);
        } 
        finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up addQuestionToPool", e);
                System.out.println(e);
            }
        }
    }
    
    public static void removeQuestionFromPool(int QID, int poolID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM questionpools " +
                        "WHERE QID = ? AND PoolID = ?";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, QID);
            ps.setInt(2, poolID);
            int update = ps.executeUpdate();
            if(1 > update){ throw new SQLException();}
        }catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: removeQuestionFromPool", e);
            System.out.println(e);
        } 
        finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up removeQuestionFromPool", e);
                System.out.println(e);
            }
        }
    }
    
    public static List<Question> getAllQuestions(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Question> qs = new ArrayList();
        
        String query = "SELECT * FROM question";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                int qID = rs.getInt("QID");
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
                qs.add(new Question(qID, qForm, qAnswer, qType, qDifficulty));
            }
            return qs;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getAllQuestions", e);
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getAllQuestions", e);
                System.out.println(e);
            }
        }
    }
    
    public static int addQuestion(Question q){
        int ID = 0;
        List<Question> qs = getAllQuestions();
        for(Question question : qs){
            if(question.compareQuestion(q)){
                return question.getID();
            }
        }
        Boolean qMult = false;
        Boolean qDiv = false;
        Boolean qAdd = false;
        Boolean qSub = false;
        switch(q.getqType()){
            case DIVISION:
                qDiv = true;
                break;
            case MULTIPLICATION:
                qMult = true;
                break;
            case ADDITION:
                qAdd = true;
                break;
            case SUBTRACTION:
                qSub = true;
                break;
        }
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String queryAdd = "INSERT INTO question " +
                            "(QFormula, QAnswer, QDifficulty, QInclMult, QInclDiv, QInclAdd, QInclSub) " +
                            "VALUES (?,?,?,?,?,?,?)";
        String queryID = "SELECT QID FROM question WHERE QFormula= ?";
        
        try {
            ps = connection.prepareStatement(queryAdd);
            ps.setString(1,q.getQuestionText());
            ps.setString(2,q.getAnswer());
            ps.setInt(3, q.getDifficulty());
            ps.setBoolean(4, qMult);
            ps.setBoolean(5, qDiv);
            ps.setBoolean(6, qAdd);
            ps.setBoolean(7, qSub);
            int update = ps.executeUpdate();
            if(1 > update){ throw new SQLException();}
            
            ps = connection.prepareStatement(queryID);
            ps.setString(1, q.getQuestionText());
            rs = ps.executeQuery();
            while(rs.next()){
                ID = rs.getInt("QID");
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addQuestion", e);
            System.out.println(e);
            return -1;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up addQuestion", e);
                System.out.println(e);
                return -1;
            }
        }
        
        return ID;
    }
    
    public static List<Attempt> getStudentAttempts(int userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Attempt attempt = null;
        Question question = null;

        
        String query = "SELECT userID, attemptID FROM assessmentattempts AS aa" +
                "JOIN assessmentattemptquestions AS aaq ON aa.attemptID = aaq.attemptID" +
                "JOIN question as q ON aaq.QID = q.QID" +
                "WHERE userID = ?"; //need to do query
        
        List<Attempt> studentAttempts = new ArrayList<>();
        
        List<Question> correctQuestions = new ArrayList<>();
        List<Question> incorrectQuestions = new ArrayList<>();
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while(rs.next()){
                //need to get: attemptID, studentID, attemptScore, attemptdate, inccorectQuestions, correctQuestions set to Attempt class; then added to list
                attempt.setStudentID(rs.getInt("userID"));
                attempt.setAttemptID(rs.getInt("attemptID"));
                
                //question.setQuestionID(rs.getInt("QID"));
                question.setQuestionText(rs.getString("QFormula"));
                question.setAnswer(rs.getString("QAnswer"));
                //question.setqType();
                question.setDifficulty(rs.getInt("QDifficulty"));
                if(rs.getBoolean("correctAnswer")){
                    correctQuestions.add(question);
                } else {
                    incorrectQuestions.add(question);
                }
                attempt.setCorrectQuestions(correctQuestions);
                attempt.setIncorrectQuestions(incorrectQuestions);
                studentAttempts.add(attempt);
            }
            return studentAttempts;
            
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
    
    public static List<Assessment> getAllAssessments() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Assessment> as = new ArrayList<>();
        
        String query = "SELECT * FROM assessment";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                int assId = rs.getInt("AssID");
                int assPool = rs.getInt("assPool");
                int assRetakes = rs.getInt("assRetakes");
                String assType = rs.getString("AssType");
                String assName = rs.getString("AssName");
                Assessment.assessmentType aType = null;
                aType = Assessment.assessmentType.valueOf(assType);
                as.add(new Assessment(assId, assName, assPool, assRetakes, aType));
            }
            return as;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: getAllAssessments", e);
            System.out.println(e);
            return null;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up getAllQuestions", e);
                System.out.println(e);
            }
        }
    }
    
    public static int addAssessment(Assessment a) {
        int id = 0;
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String queryAdd = "INSERT INTO assessment " +
                          "(AssPool, AssRetakes, AssType, AssName) " +
                          "(?,?,?,?)";
        String queryID = "SELECT AssID FROM assessment WHERE AssName = ?";
        
        try {
            ps = connection.prepareStatement(queryAdd);
            ps.setInt(1, a.getPoolID());
            ps.setInt(2, a.getRetakes());
            ps.setString(3, a.getaType().toString());
            ps.setString(4, a.getAssessmentName());
            int update = ps.executeUpdate();
            if (1 > update) { throw new SQLException();}
            
            ps = connection.prepareStatement(queryID);
            ps.setString(1, a.getAssessmentName());
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("AssID");
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** SQLException: addAssessment", e);
            System.out.println(e);
            return -1;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, "*** SQLException: cleaning up addAssessment", e);
                System.out.println(e);
                return -1;
            }
        }
        return id;
    }
}

    
    
    
    

