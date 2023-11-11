<%-- 
    Document   : admin_home
    Created on : Jul 11, 2023, 11:43:27 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector" %>
<%@page import="entity.Books" %>
<%@page import="entity.Users" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <%
                    Users user= (Users) request.getAttribute("user");
                    Vector<Books> vector= (Vector<Books>) request.getAttribute("books");
                    Vector<String> category= (Vector<String>) request.getAttribute("category");
                    String message= (String) request.getAttribute("message");
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
                            <input type="hidden" name="userId" value="<%=user.getUserId()%>">
                            <input name="search" type="search" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search...">
                            <div class="input-group-append">
                                <button type="submit" class="btn btn-secondary btn-number">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                    <form>
                        <a class="btn btn-success btn-sm ml-3" href="/CartController?action=viewCart&userId=<%=user.getUserId()%>">
                            <i class="fa fa-shopping-cart"></i> Cart
                            <span class="badge badge-light"></span>
                        </a>
                    </form>
                </div>
            </div>
        </nav>
        <!--end of menu-->

        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading">Book Store</h1>
            </div>
        </section>

        <div class="row">
            <!-- Left side: Categories -->
            <div class="col-sm-2" style="margin-left: 4%;">
                <nav class="sidebar">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <h5>Categories</h5>
                        </li>
                        <%
                        for (String s : category) {
                        %>
                        <li class="nav-item">
                            <a class="nav-link" href="/BookController?action=showBooksByCategory&category=<%=s%>&userId=<%=user.getUserId()%>"><%=s%></a>
                        </li>
                        <%
                        }
                        %>
                    </ul>
                </nav>
            </div>

            <!-- Right side: List of Books -->
            <div class="col-sm-9">
                <article style="color: red"><%=message!= null? message: ""%></article>
                <div class="col-sm-12">
                    <div class="row">
                        <%
                        for (Books b : vector) {
                        %>
                        <div class="col-12 col-md-6 col-lg-4">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title show_txt"><a href="/BookController?action=bookDetail&bookId=<%=b.getBookId()%>&userId=<%=user.getUserId()%>"><%=b.getTitle()%></a></h4>
                                    <p class="card-text show_txt"><%=b.getAuthor()%></p>
                                    <div class="row">
                                        <div class="col">
                                            <p class="btn btn-danger btn-block">$<%=b.getPrice()%></p>
                                        </div>
                                        <div class="col">
                                            <form action="CartController?action=addToCart&userId=<%=user.getUserId()%>" method="post">
                                                <input type="hidden" name="bookId" value="<%=b.getBookId()%>">
                                                <input type="hidden" name="quantity" value="1">
                                                <input class="btn btn-success btn-block" type="submit" value="Add to cart" />
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%
                        }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
