<%-- 
    Document   : user_cart
    Created on : Nov 10, 2023, 9:13:51 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.*" %>
<%@page import="java.util.Vector" %>
<%@page import="controller.CartController" %>

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
            Vector<Books> book = (Vector<Books>) request.getAttribute("book");
            CartController cartController = (CartController) request.getAttribute("cartC");
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

        <section class="h-100 h-custom">
            <div class="container h-100 py-5">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Title</th>
                                        <th scope="col">Author</th>
                                        <th scope="col">Category</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Price</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Books b : book) { %>
                                    <tr>
                                        <td><%= b.getTitle() %></td>
                                        <td><%= b.getAuthor() %></td>
                                        <td><%= b.getCategory() %></td>
                                        <td>
                                            <div class="d-flex flex-row">
                                                <input id="form1" min="0" name="quantity" value="<%=cartController.findQuantityInCart(b.getBookId(),user.getUserId())%>"
                                                       type="number" class="form-control form-control-sm" style="width: 50px;" />
                                            </div>
                                        </td>
                                        <td><%= b.getPrice() %></td>
                                        <td>
                                            <form action="CartController?action=removeBookInCart&userId=<%=user.getUserId()%>" method="post">
                                                <input type="hidden" name="cartBookId" value="<%= b.getBookId() %>">
                                                <input type="submit" class="btn btn-danger" value="Delete" />
                                            </form>
                                        </td>
                                    </tr>
                                    <% } %>
                                    <tr>
                                        <td colspan="4"><strong>Total</strong></td>
                                        <td><strong>$<span><%=(double) request.getAttribute("total")%></span></strong></td>
                                        <td></td> <!-- Empty column for the button alignment -->
                                    </tr>
                                </tbody>
                            </table>

                            <%if (cart.size() > 0) {%>
                            <form action="CartController?updateCart&userId=<%=user.getUserId()%>" method="post">
                                <input type="hidden" name="quantity" value="quantity">
                                <input type="submit" class="btn btn-danger" value="Update Cart" />
                            </form>

                            <form>
                                <div id="cid_2" class="form-input-wide">
                                    <div data-align="auto" class="form-buttons-wrapper form-buttons-auto   jsTest-button-wrapperField"><button id="input_2" type="submit" class="form-submit-button form-submit-button-simple_blue submit-button jf-form-buttons jsTest-submitField" data-component="button" data-content="" name="update" value="buy" >Buy</button></div>
                                </div>
                            </form>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
