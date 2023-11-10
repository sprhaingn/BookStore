/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAOBooks;
import dao.DAOUsers;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    DAOUsers daoUser = new DAOUsers();
    DAOBooks daoBooks = new DAOBooks();

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
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "UserSignUp":
                SignUpPage(request, response);
                break;
            case "UserForgetPassword":
                ResetPasswordPage(request, response);
                break;
            default:
                LoginPage(request, response);
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
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "UserSignUp":
                UserSignUp(request, response);
                break;
            case "UserLogin":
                UserLogin(request, response);
                break;
            case "UserForgetPassword":
                ResetPassword(request, response);
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

    private void LoginPage(HttpServletRequest request, HttpServletResponse response) {
        getRequestDispatch(request, response, "/login/login.jsp");
    }

    private void SignUpPage(HttpServletRequest request, HttpServletResponse response) {
        getRequestDispatch(request, response, "/login/signup.jsp");
    }

    private void ResetPasswordPage(HttpServletRequest request, HttpServletResponse response) {
        getRequestDispatch(request, response, "/login/rspassword.jsp");
    }

    private void UserLogin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Users user = daoUser.checkLogin(username, password);

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("message", "Please fill in all the fields");
            getRequestDispatch(request, response, "/login/login.jsp");
        } else if (!isValidPassword(password.trim())) {
            request.setAttribute("message", "Password length must be between 8 and 24");
            getRequestDispatch(request, response, "/login/login.jsp");
        } else {
            if (user == null) {
                request.setAttribute("username", username);
                request.setAttribute("message", "Wrong username or password");
                getRequestDispatch(request, response, "/login/login.jsp");
            } else {
                request.setAttribute("user", user);
                request.setAttribute("books", daoBooks.getAllBooks());
                request.setAttribute("category", daoBooks.getBooksCategory());
                if (user.getRole().trim().equalsIgnoreCase("admin")) {
                    getRequestDispatch(request, response, "/admin/admin_home.jsp");
                } else {
                    getRequestDispatch(request, response, "/user/user_home.jsp");
                }
            }
        }
    }

    private void UserSignUp(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repass = request.getParameter("repass");

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || repass.isEmpty()) {
            request.setAttribute("message", "Please fill in all the fields");
        } else if (!isValidEmail(email)) {
            request.setAttribute("message", "Invalid email format");
        } else if (!isValidPassword(password)) {
            request.setAttribute("message", "Password length must be between 8 and 24");
        } else if (!password.trim().equals(repass.trim())) {
            request.setAttribute("message", "Passwords do not match");
        } else {
            Users user = daoUser.checkUserExist(username, email);

            if (user != null) {
                request.setAttribute("message", "Username or Email already exists");
            } else {
                user = new Users(firstName, lastName, email, username, password, email);
                daoUser.UserSignUp(user);
                request.setAttribute("message", "Sign up successful");
            }
        }
        getRequestDispatch(request, response, "/login/signup.jsp");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        int minLength = 8;
        int maxLength = 24;

        return password.length() >= minLength && password.length() <= maxLength;
    }

    private void ResetPassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        String newpass = request.getParameter("newpass");

        if (!isValidPassword(newpass)) {
            request.setAttribute("message", "Invalid new password format");
        } else if (!password.equals(newpass)) {
            request.setAttribute("message", "Re-enter new password");
        } else {
            String email = request.getParameter("email");
            Users user = daoUser.findEmail(email);

            if (user != null) {
                daoUser.ResetPassword(email, password);
                request.setAttribute("message", "Reset Password successfully");
            } else {
                request.setAttribute("message", "Can not find this email");
            }
        }
        getRequestDispatch(request, response, "/login/rspassword.jsp");
    }

    private void getRequestDispatch(HttpServletRequest request, HttpServletResponse response, String view) {
        RequestDispatcher rd = request.getRequestDispatcher(view);
        try {
            rd.forward(request, response);

        } catch (ServletException ex) {
            Logger.getLogger(UserController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(UserController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
