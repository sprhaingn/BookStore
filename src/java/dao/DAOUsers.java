/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.DBConnect;
import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DAOUsers extends DBConnect {

    PreparedStatement pre = null;
    ResultSet rs = null;

    public Users checkLogin(String username, String password) {
        Users user = null;
        try {
            String USER_LOGIN = "select*from Users where username=? and password=?";
            pre = conn.prepareStatement(USER_LOGIN);

            pre.setString(1, username);
            pre.setString(2, password);

            rs = pre.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);
                String role = rs.getString(7);

                user = new Users(userId, firstName, lastName, email, username, password, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public void UserSignUp(Users user) {
        try {
            String USER_SIGNUP = "INSERT INTO [dbo].[Users]\n"
                    + "           ([first_name]\n"
                    + "           ,[last_name]\n"
                    + "           ,[email]\n"
                    + "           ,[username]\n"
                    + "           ,[password]\n"
                    + "           ,[role])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,'user')";

            pre = conn.prepareStatement(USER_SIGNUP);

            pre.setString(1, user.getFirstName());
            pre.setString(2, user.getLastName());
            pre.setString(3, user.getEmail());
            pre.setString(4, user.getUsername());
            pre.setString(5, user.getPassword());

            pre.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Users checkUserExist(String username, String email) {
        Users user = null;
        try {
            String USER_LOGIN = "select*from Users where username=? or email=?";
            pre = conn.prepareStatement(USER_LOGIN);

            pre.setString(1, username);
            pre.setString(2, email);

            rs = pre.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(5);
                String role = rs.getString(7);

                user = new Users(userId, firstName, lastName, email, username, password, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public Users findEmail(String email) {
        Users user = null;
        try {
            String FIND_EMAIL = "select*from Users where email=?";
            pre = conn.prepareStatement(FIND_EMAIL);
            pre.setString(1, email);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String username = rs.getString(5);
                String role = rs.getString(7);

                user = new Users(userID, firstName, lastName, email, username, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
        public Users findId(int id) {
        Users user = null;
        try {
            String FIND_USERID = "select*from Users where user_id=?";
            pre = conn.prepareStatement(FIND_USERID);
            pre.setInt(1, id);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(4);
                String username = rs.getString(5);
                String role = rs.getString(7);

                user = new Users(userID, firstName, lastName, email, username, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public Users findUsername(String username) {
        Users user = null;
        try {
            String FIND_USERNAME = "select*from Users where username=?";
            pre = conn.prepareStatement(FIND_USERNAME);
            pre.setString(1, username);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String email = rs.getString(5);
                String role = rs.getString(7);

                user = new Users(userID, firstName, lastName, email, username, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }


    public void ResetPassword(String email, String password) {
        try {
            String RESET_PASSWORD = "update users set password=? where email=?";
            pre = conn.prepareStatement(RESET_PASSWORD);
            pre.setString(1, password);
            pre.setString(2, email);

            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsers.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
