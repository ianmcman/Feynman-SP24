/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import data.FeynmanDB;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mcman
 */
public class Validation {

    public static String validateUserNameUnique(String input) {
        String message = "";
        try {
            boolean nameExists = FeynmanDB.nameExists(input);
            if (nameExists == true) {
                message += "That username already exists. Please use a different username. ";
            }
        } catch (Exception ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE,null, ex);
            message += "Exception " + ex + " on validateUsernameUnique. ";
        } 
        return message;
    }

    public static String validateUserName(String input) {
        String message = "";
        if (input.length() < 4) {
            message += "Username must be at least 4 characters long. ";
        }
        if (input.length() > 30) {
            message += "Username must be at most 30 characters long. ";
        }
        return message;
    }

    public static String validatePassword(String input) {
        String message = "";

        if (input.length() <= 10) {
            message += "Password must be more than 10 characters long. ";
        }
        
        Pattern upper = Pattern.compile(".*[A-Z].*");
        Matcher m = upper.matcher(input);
        boolean b = m.matches();
        if (!b) {
            message += "Password must contain an uppercase letter. ";
        }
        
        Pattern lower = Pattern.compile(".*[a-z].*");
        m = lower.matcher(input);
        b = m.matches();
        if (!b) {
            message += "Password must contain a lowercase letter. ";
        }
        
        Pattern number = Pattern.compile(".*[0-9].*");
        m = number.matcher(input);
        b = m.matches();
        if (!b) {
            message += "Password must contain a number. ";
        }
                
        Pattern specialCharacters = Pattern.compile(".*[!@#$%^&*].*");
        m = specialCharacters.matcher(input);
        b = m.matches();
        if (!b) {
            message += "Password must contain at least one of the following: !, @, #, $, %, ^, &, or *. ";
        }
        
        
        return message;
    }

}
