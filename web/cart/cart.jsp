<%-- 
    Document   : admin_cart
    Created on : Nov 9, 2023, 11:42:54 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.*" %>
<%@page import="dao.DAOBooks" %>
<%@page import="java.util.Vector" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <title>Cart</title>
        <%
            Users user = (Users) request.getAttribute("user");
            Vector<Cart> cart = (Vector<Cart>) request.getAttribute("cart");
            DAOBooks daoBooks = new DAOBooks();
        %>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="/BookController?userId=<%=user.getUserId()%>">BookStore</a>
                <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                    <ul class="navbar-nav m-auto">
                        <li class="nav-item" >
                            <a class="nav-link" href="#"><%=user.getFirstName()%></a>
                        </li>
                        <li class="nav-item" >
                            <a class="nav-link" href="/UserController">Log Out</a>
                        </li>
                        <li class="nav-item" >
                            <a class="nav-link" href="#">Order History</a>
                        </li>
                    </ul>

                    <form action="/BookController" method="Get" class="form-inline my-2 my-lg-0">
                        <div class="input-group input-group-sm">
                            <input type="hidden" name="action" value="search">
                            <input type="hidden" name="username" value="<%=user.getUsername()%>">
                            <input name="search" type="search" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search...">
                            <div class="input-group-append">
                                <button type="submit" class="btn btn-secondary btn-number">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                    <form>
                        <a class="btn btn-success btn-sm ml-3" href="#">
                            <i class="fa fa-shopping-cart"></i> Cart
                            <span class="badge badge-light"></span>
                        </a>
                    </form>
                </div>
            </div>
        </nav>
                            
        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading">Book Store</h1>
            </div>
        </section>
        <section class="h-100 h-custom">
            <div class="container h-100 py-5">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col" class="h5">Shopping Bag</th>
                                        <th scope="col">Category</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Price</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Cart c : carts) {
                                        int bookIdFromCart = c.getBookId();
                                        Book bookDetails = daoBooks.findBookById(bookIdFromCart);
                                    %>
                                    <tr>
                                        <th scope="row">
                                             Your content for each cart item goes here 
                                            <div class="d-flex align-items-center">
                                                <img src="#" class="img-fluid rounded-3"
                                                     style="width: 120px;" alt="Book">
                                                <div class="flex-column ms-4">
                                                    <p class="mb-2"><%= bookDetails != null ? bookDetails.getTitle() : "" %></p>
                                                    <p class="mb-0"><%= bookDetails != null ? bookDetails.getAuthor() : "" %></p>
                                                </div>
                                            </div>
                                        </th>
                                        <td class="align-middle">
                                             Your category content goes here 
                                            <p class="mb-0" style="font-weight: 500;"><%= bookDetails != null ? bookDetails.getCategory() : "" %></p>
                                        </td>
                                        <td class="align-middle">
                                             Your quantity input goes here 
                                            <div class="d-flex flex-row">
                                                <button class="btn btn-link px-2"
                                                        onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                                                    <i class="fas fa-minus"></i>
                                                </button>
                                                <input id="form_<%= cart.getBookId() %>" min="0" name="quantity" value="2" type="number"
                                                       class="form-control form-control-sm" style="width: 50px;" />
                                                <button class="btn btn-link px-2"
                                                        onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                                                    <i class="fas fa-plus"></i>
                                                </button>
                                            </div>
                                        </td>
                                        <td class="align-middle">
                                             Your price content goes here 
                                            <p class="mb-0" style="font-weight: 500;"><%= bookDetails != null ? bookDetails.getPrice() : "" %></p>
                                        </td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
