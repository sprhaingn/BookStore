/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAOStock;
import dao.DAOBooks;
import dao.DAOUsers;
import entity.Books;
import entity.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    DAOUsers daoUser = new DAOUsers();
    DAOBooks daoBooks = new DAOBooks();
    DAOStock daoStock = new DAOStock();

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
            case "bookDetail":
                showBookDetail(request, response);
                break;
            case "showBooksByCategory":
                showBooksByCategory(request, response);
                break;
            case "search":
                searchBook(request, response);
                break;
            case "crudhome":
                showCRUDForm(request, response);
                break;
            case "addBookForm":
                addBookForm(request, response);
                break;
            case "editBookForm":
                editBookForm(request, response);
                break;
            default:
                showBooks(request, response);
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
            case "addBook":
                insertBooks(request, response);
                break;
            case "deleteBook":
                deleteBook(request, response);
                break;
            case "editBook":
                editBook(request, response);
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

    private void showBooks(HttpServletRequest request, HttpServletResponse response) {
        Vector<Books> vector = daoBooks.getAllBooks();
        int userId = Integer.parseInt(request.getParameter("userId"));

        request.setAttribute("books", vector);
        request.setAttribute("category", daoBooks.getBooksCategory());
        request.setAttribute("user", daoUser.findId(userId));

        if (daoUser.findId(userId).getRole().trim().equalsIgnoreCase("admin")) {
            getRequestDispatch(request, response, "/admin/admin_home.jsp");
        } else {
            getRequestDispatch(request, response, "/user/user_home.jsp");
        }
    }

    private void showBooksByCategory(HttpServletRequest request, HttpServletResponse response) {
        String category = request.getParameter("category");
        int userId = Integer.parseInt(request.getParameter("userId"));

        request.setAttribute("books", daoBooks.getAllBooksByCategory(category));
        request.setAttribute("category", daoBooks.getBooksCategory());
        request.setAttribute("user", daoUser.findId(userId));

        if (daoUser.findId(userId).getRole().trim().equalsIgnoreCase("admin")) {
            getRequestDispatch(request, response, "/admin/admin_home.jsp");
        } else {
            getRequestDispatch(request, response, "/user/user_home.jsp");
        }
    }

    private void showBookDetail(HttpServletRequest request, HttpServletResponse response) {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        Books book = daoBooks.findBookById(bookId);

        request.setAttribute("user", daoUser.findId(userId));
        request.setAttribute("book", book);
        request.setAttribute("stock", daoStock.findQuantityByBookID(bookId));

        if (daoUser.findId(userId).getRole().trim().equalsIgnoreCase("admin")) {
            getRequestDispatch(request, response, "/book/admin_book_detail.jsp");
        } else {
            getRequestDispatch(request, response, "/book/book_detail.jsp");
        }
    }

    private void searchBook(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("search");

        if (title == null || title.isEmpty()) {
            showBooks(request, response);
        } else {
            int userId = Integer.parseInt(request.getParameter("userId"));

            try {
                Vector<Books> searchResults = daoBooks.searchBooks(title);

                if (searchResults.isEmpty()) {
                    request.setAttribute("message", "No books found.");
                } else {
                    request.setAttribute("books", searchResults);
                    request.setAttribute("category", daoBooks.getBooksCategory());
                    request.setAttribute("user", daoUser.findId(userId));
                    request.setAttribute("message", "Search results:");

                    getRequestDispatch(request, response, "user/user_home.jsp");
                    getRequestDispatch(request, response, "admin/admin_home.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "An error occurred while searching for books.");
                showBooks(request, response);
            }
        }
    }

    //ADMIN FUNCTION
    //GET FUNCTION
    private void showCRUDForm(HttpServletRequest request, HttpServletResponse response) {
        Vector<Books> vector = daoBooks.getAllBooks();
        int userId = Integer.parseInt(request.getParameter("userId"));

        request.setAttribute("category", daoBooks.getBooksCategory());
        request.setAttribute("books", vector);
        request.setAttribute("user", daoUser.findId(userId));

        getRequestDispatch(request, response, "/admin/crud_home.jsp");
    }

    private void addBookForm(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        request.setAttribute("user", daoUser.findId(userId));

        getRequestDispatch(request, response, "/book/add_book.jsp");
    }

    private void editBookForm(HttpServletRequest request, HttpServletResponse response) {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        Books book = daoBooks.findBookById(bookId);

        request.setAttribute("user", daoUser.findId(userId));
        request.setAttribute("books", book);

        getRequestDispatch(request, response, "/book/edit_book.jsp");
    }

    //POST FUNCTION
    private void insertBooks(HttpServletRequest request, HttpServletResponse response) {
        Vector<Books> vector = daoBooks.getAllBooks();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        request.setAttribute("user", daoUser.findId(userId));
        try {
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (title.isEmpty() || author.isEmpty() || category.isEmpty() || quantity < 1 || price < 1) {
                request.setAttribute("message", "Please fill out the form completely and enter valid data.");
            } else {
                boolean existed = false;

                for (Books b : vector) {
                    if (b.getTitle().equals(title) && b.getAuthor().equals(author)) {
                        existed = true;
                        break;
                    }
                }

                if (existed == true) {
                    request.setAttribute("message", "This book already exists.");
                    getRequestDispatch(request, response, "book/add_book.jsp");
                } else {
                    daoBooks.insertBookWithStock(title, author, category, price, description, quantity);
                    request.setAttribute("message", "Inserted successfully!");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid price or quantity. Please enter valid numeric values.");
        }
        getRequestDispatch(request, response, "book/add_book.jsp");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        daoBooks.deleteBooksById(bookId);

        getRequestDispatch(request, response, "/admin/crud_home.jsp");
        response.sendRedirect("/BookController?action=crudhome&userId=" + userId);
    }

    private void editBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            Books book = daoBooks.findBookById(bookId);
            int userId = Integer.parseInt(request.getParameter("userId"));

            request.setAttribute("books", book);
            request.setAttribute("user", daoUser.findId(userId));

            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));

            if (quantity < 0) {
                request.setAttribute("message", "Quantity must be greater than or equal to 0!");
            } else if (price <= 0) {
                request.setAttribute("message", "Price must be greater than 0!");
            } else {
                daoBooks.updateBook(price, bookId);
                daoStock.updateStock(quantity, bookId);
                request.setAttribute("message", "Update book successfully!");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid input. Please provide valid quantity and price.");
        }
        getRequestDispatch(request, response, "/book/edit_book.jsp");
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
