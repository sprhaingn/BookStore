<%-- 
    Document   : book_detail
    Created on : Jul 15, 2023, 3:34:40 PM
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
        <title>Book Detail</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <%
                    Users user= (Users) request.getAttribute("user");
                    Books book= (Books) request.getAttribute("book");
                    int stock= (int) request.getAttribute("stock");
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
                            <a class="nav-link" href="/BookController?action=crudhome&username=<%=user.getUsername()%>">Edit Library</a>
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
        <!--end of menu-->
        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading">Book Store</h1>
            </div>
        </section>

        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="container">
                        <div class="card">
                            <div class="row">
                                <aside class="col-sm-5 border-right">
                                    <article class="gallery-wrap"> 
                                        <div class="img-big-wrap">
                                            <div> <a href="#"><img src=""></a></div>
                                        </div> <!-- slider-product.// -->
                                        <div class="img-small-wrap">
                                        </div> <!-- slider-nav.// -->
                                    </article> <!-- gallery-wrap .end// -->
                                </aside>
                                <aside class="col-sm-7">
                                    <article class="card-body p-5">
                                        <h3 class="title mb-3"><%=book.getTitle()%></h3>

                                        <p class="price-detail-wrap"> 
                                            <span class="price h3 text-warning"> 
                                                <span class="currency">US $</span><span class="num"><%=book.getPrice()%></span>
                                            </span> 
                                        </p>
                                        <dl class="item-property">
                                            <dt>Description</dt>
                                            <dd><p>
                                                    <%=book.getDescription()%>
                                                </p>
                                            </dd>
                                        </dl>
                                        <hr>
                                        <form action="CartController?action=addToCart&userId=<%=user.getUserId()%>" method="post">
                                            <input type="hidden" name="bookId" value="<%=book.getBookId()%>">
                                            <div class="row">
                                                <div class="col-sm-5">
                                                    <dl class="param param-inline">
                                                        <dt>Quantity: </dt>
                                                        <dd>
                                                            <input type="number" name="quantity" class="form-control form-control-sm" style="width:70px;" min="1" max="<%=stock%>" value="1">
                                                        </dd>
                                                    </dl>
                                                </div>
                                            </div> 
                                            <hr>
                                            <div class="col">
                                                <input class="btn btn-lg btn-primary text-uppercase" type="submit" value="Add to cart" />
                                            </div>
                                        </form>
                                        <article style="color: red"><%=message!= null? message: ""%></article>
                                    </article> 
                                </aside>
                            </div> 
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
