/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DAOStock extends DBConnect {

    PreparedStatement pre = null;
    ResultSet rs = null;

    public int findQuantityByBookID(int bookId) {
        int quantity = 0;
        try {
            String FIND_BOOK_QUANTITY = ("select b.quantity from Stock b \n"
                    + "join Stock s on b.book_id = s.book_id \n"
                    + "where b.book_id =?");
            pre = conn.prepareStatement(FIND_BOOK_QUANTITY);
            pre.setInt(1, bookId);
            rs = pre.executeQuery();
            while (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantity;
    }

    public void updateStock(int quantity, int bookId) {
        try {
            String UPDATE_BOOK = "UPDATE [dbo].[Stock]\n"
                    + "   SET \n"
                    + "      [quantity] = ?\n"
                    + " WHERE book_id = ?";

            pre = conn.prepareStatement(UPDATE_BOOK);

            pre.setInt(1, quantity);
            pre.setInt(2, bookId);

            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
