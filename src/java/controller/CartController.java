/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAOBooks;
import dao.DAOCart;
import dao.DAOStock;
import dao.DAOUsers;
import entity.Books;
import entity.Cart;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    DAOStock daoStock = new DAOStock();
    DAOBooks daoBooks = new DAOBooks();
    DAOCart daoCart = new DAOCart();
    DAOUsers daoUser = new DAOUsers();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "viewCart":
                viewCart(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "addToCart":
                addToCart(request, response);
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addToCart(HttpServletRequest request, HttpServletResponse response) {
        Vector<Books> vector = daoBooks.getAllBooks();
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        request.setAttribute("book", daoBooks.findBookById(bookId));
        request.setAttribute("books", vector);
        request.setAttribute("category", daoBooks.getBooksCategory());
        request.setAttribute("user", daoUser.findId(userId));

        if (daoCart.findQuantityInCart(userId, bookId) >= daoStock.findQuantityByBookID(bookId)) {
            request.setAttribute("message", "The requested quantity exceeds the available stock for this item.");
        } else if (daoCart.findExistCart(userId, bookId) != true) {
            daoCart.addToCart(userId, bookId, quantity);
            request.setAttribute("message", "Add to cart successfully!");
        } else {
            while (quantity == 1) {
                quantity = daoCart.findQuantityInCart(userId, bookId) + 1;
            }
            daoCart.updateCart(quantity, userId, bookId);
            request.setAttribute("message", "Add to cart successfully!");
        }
        if (daoUser.findId(userId).getRole().trim().equalsIgnoreCase("admin")) {
            getRequestDispatch(request, response, "/admin/admin_home.jsp");
        } else {
            getRequestDispatch(request, response, "/user/user_home.jsp");
        }
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        Vector<Books> book = new Vector<>();
        Vector<Cart> cart = daoCart.findCartById(userId);
        Users user = daoUser.findId(userId);
        
        request.setAttribute("user", user);
        request.setAttribute("cart", cart);
        request.setAttribute("book", book);
        request.setAttribute("cartC", new CartController());

        double total = 0;
        for (Cart c : cart) {
            book.add(daoBooks.findBookById(c.getBookId()));
            double price = daoBooks.findBookById(c.getBookId()).getPrice();
            int quantity = c.getQuantity();
            total += quantity * price;
        }
        request.setAttribute("total", total);

        getRequestDispatch(request, response, "/cart/user_cart.jsp");
    }

    //SEND TO VIEW
    private void getRequestDispatch(HttpServletRequest request, HttpServletResponse response, String view) {
        RequestDispatcher rd = request.getRequestDispatcher(view);
        try {
            rd.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
