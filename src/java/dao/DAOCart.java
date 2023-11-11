/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.DBConnect;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author admin
 */
public class DAOCart extends DBConnect {

    PreparedStatement pre = null;
    ResultSet rs = null;

    public void addToCart(int userId, int bookId, int quantity) {
        String ADD_TO_CART = "INSERT INTO [dbo].[Cart]\n"
                + "           ([user_id]\n"
                + "           ,[book_id]\n"
                + "           ,[quantity])\n"
                + "     VALUES\n"
                + "           (?"
                + "           ,?"
                + "           ,?)";
        try {
            pre = conn.prepareStatement(ADD_TO_CART);

            pre.setInt(1, userId);
            pre.setInt(2, bookId);
            pre.setInt(3, quantity);

            pre.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean findExistCart(int userId, int bookId) {
        Cart cart = null;
        try {
            String FIND_EXIST_CART = "select *\n"
                    + "from Cart \n"
                    + "where user_id =? and book_id =?";
            pre = conn.prepareStatement(FIND_EXIST_CART);

            pre.setInt(1, userId);
            pre.setInt(2, bookId);
            rs = pre.executeQuery();
            while (rs.next()) {
                userId = rs.getInt(1);
                bookId = rs.getInt(2);
                int quantity = rs.getInt(3);

                cart = new Cart(userId, bookId, quantity);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cart != null;
    }

    public int findQuantityInCart(int userId, int bookId) {
        int quantity = 0;
        try {
            String FIND_EXIST_CART = "select quantity\n"
                    + "from Cart \n"
                    + "where user_id =? and book_id =?";
            pre = conn.prepareStatement(FIND_EXIST_CART);

            pre.setInt(1, userId);
            pre.setInt(2, bookId);
            rs = pre.executeQuery();
            while (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantity;
    }

    public void updateCart(int quantity, int userId, int bookId) {
        String UPDATE_CART = "UPDATE [dbo].[Cart]\n"
                + "   SET \n"
                + "      [quantity] = ?\n"
                + " WHERE \n"
                + " user_id = ? and book_id = ?";
        try {
            pre = conn.prepareStatement(UPDATE_CART);

            pre.setInt(1, quantity);
            pre.setInt(2, userId);
            pre.setInt(3, bookId);

            pre.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<Cart> findCartById(int userId) {
        Vector<Cart> cart = new Vector<>();
        try {
            String FIND_EXIST_CART = "select *\n"
                    + "from Cart \n"
                    + "where user_id =?";
            pre = conn.prepareStatement(FIND_EXIST_CART);

            pre.setInt(1, userId);
            rs = pre.executeQuery();
            while (rs.next()) {
                userId = rs.getInt(1);
                int bookId = rs.getInt(2);
                int quantity = rs.getInt(3);

                Cart c = new Cart(userId, bookId, quantity);
                cart.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cart;
    }

    public Cart findCart(int userId, int bookId) {
        Cart cart = null;
        try {
            String FIND_CART_BY_BOOK_ID_AND_USERID = "select * from Cart \n"
                    + "where user_id = ? and book_id = ?";
            pre = conn.prepareStatement(FIND_CART_BY_BOOK_ID_AND_USERID);

            pre.setInt(1, userId);
            pre.setInt(2, bookId);

            rs = pre.executeQuery();
            while (rs.next()) {
                int quantity = rs.getInt(3);

                cart = new Cart(userId, bookId, quantity);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cart;
    }

    public void deleteFromCart(int bookId, int userID) {
        try {
            String DELETE_BY_BOOK_ID = "delete from cart where book_id=? AND user_id=? ";
            pre = conn.prepareStatement(DELETE_BY_BOOK_ID);
            pre.setInt(1, bookId);
            pre.setInt(2, userID);

            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    public static void main(String[] args) {
        DAOCart dao = new DAOCart();
        int userId = 1;
        int bookId = 1;
        Cart c = dao.findCart(userId, bookId);

        System.out.println(c);
    }
}
