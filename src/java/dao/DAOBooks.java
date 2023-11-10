/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.DBConnect;
import entity.Books;
import java.sql.Connection;
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
public class DAOBooks extends DBConnect {

    PreparedStatement pre = null;
    ResultSet rs = null;

    public Vector<Books> getAllBooks() {
        Vector<Books> vector = new Vector<>();

        String FIND_ALL_BOOK = "select * from Books";
        try {
            pre = conn.prepareStatement(FIND_ALL_BOOK);
            rs = pre.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String category = rs.getString(4);
                double price = rs.getDouble(5);
                String description = rs.getString(6);

                Books books = new Books(bookId, title, author, category, price, description);
                vector.add(books);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<String> getBooksCategory() {
        Vector<String> vector = new Vector<>();

        String GET_CATEGORY = "select distinct category_name from Books";
        try {
            pre = conn.prepareStatement(GET_CATEGORY);

            rs = pre.executeQuery();
            while (rs.next()) {
                vector.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Books> getAllBooksByCategory(String category) {
        Vector<Books> vector = new Vector<>();
        try {
            String BOOKS_BYCATEGORY = "select*from Books where category_name =?";
            PreparedStatement pre = conn.prepareStatement(BOOKS_BYCATEGORY);
            pre.setString(1, category);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                double price = rs.getDouble(5);
                String description = rs.getString(6);

                Books books = new Books(bookId, title, author, category, price, description);
                vector.add(books);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Books findBookById(int id) {
        Books book = null;
        try {
            String FIND_BOOKID = "select*from Books where book_id=?";
            pre = conn.prepareStatement(FIND_BOOKID);
            pre.setInt(1, id);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String category = rs.getString(4);
                double price = rs.getDouble(5);
                String description = rs.getString(6);

                book = new Books(id, title, author, category, price, description);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }

    public Vector<Books> searchBooks(String bookTitle) {
        Vector<Books> vector = new Vector<>();

        String SEARCH_BOOK = "select*from Books where title like ?";
        try {
            pre = conn.prepareStatement(SEARCH_BOOK);
            pre.setString(1, "%" + bookTitle + "%");
            rs = pre.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String category = rs.getString(4);
                double price = rs.getDouble(5);
                String description = rs.getString(6);

                Books books = new Books(bookId, title, author, category, price, description);
                vector.add(books);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public void insertBookWithStock(String title, String author, String category, double price, String description, int quantity) {
        try {
            String INSERT_BOOKS = "INSERT INTO Books (title, author, category_name, price, description) VALUES (?, ?, ?, ?, ?)";
            pre = conn.prepareStatement(INSERT_BOOKS, pre.RETURN_GENERATED_KEYS);

            pre.setString(1, title);
            pre.setString(2, author);
            pre.setString(3, category);
            pre.setDouble(4, price);
            pre.setString(5, description);

            int affectedRows = pre.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = pre.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int bookId = generatedKeys.getInt(1);

                    String INSERT_STOCK = "INSERT INTO Stock (book_id, quantity) VALUES (?, ?)";
                    pre = conn.prepareStatement(INSERT_STOCK);

                    pre.setInt(1, bookId);
                    pre.setInt(2, quantity);

                    pre.executeUpdate();

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteBooksById(int bookId) {
        try {
            String DELTE_BOOKS = "BEGIN TRANSACTION;\n"
                    + "\n"
                    + "DELETE FROM Stock\n"
                    + "WHERE book_id = ?;\n"
                    + "\n"
                    + "DELETE FROM Books\n"
                    + "WHERE book_id = ?;\n"
                    + "\n"
                    + "COMMIT;";

            pre = conn.prepareStatement(DELTE_BOOKS);

            pre.setInt(1, bookId);
            pre.setInt(2, bookId);

            pre.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBook(double price, int bookId) {
        try {
            String UPDATE_BOOK = "UPDATE [dbo].[Books]\n"
                    + "   SET \n"
                    + "      [price] = ?\n"
                    + " WHERE book_id = ?";
            
            pre = conn.prepareStatement(UPDATE_BOOK);
            
            pre.setDouble(1, price);
            pre.setInt(2, bookId);
            
            pre.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(DAOBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
