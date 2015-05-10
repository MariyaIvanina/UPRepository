<%-- 
    Document   : login
    Created on : Mar 14, 2015, 11:33:45 PM
    Author     : Mary
--%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                padding-top : 80px !important;
            }

       </style>
       <link href="<c:url value="${theme}"/>" rel="stylesheet" type="text/css">
        <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
    </head>
    <body>
   <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
               
            </div>
            <div class="navbar-collapse collapse navbar-responsive-collapse">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="label.theme"/>  <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                           <li><a href="${pageContext.request.contextPath}/home/simplex">simplex</a></li>
                            <li><a href="${pageContext.request.contextPath}/home/united">united</a></li>
                        </ul>
                    </li>
                   
                </ul>
                <ul class="nav navbar-nav navbar-right">  
                    <li> <a href="${pageContext.request.contextPath}/home" title="Home"><spring:message code="label.back"/></a></li>
                <c:if test="${currentuser == null}">
                   <li><a href="${pageContext.request.contextPath}/login" title ="LogIn" id="LoginPopup"><spring:message code="label.login"/></a></li>
                   <li><a href="${pageContext.request.contextPath}/registration" title="Registration"><spring:message code="label.registration"/></a></li>
        </c:if>
        <c:if test="${currentuser != null}">
        <li>${currentuser}</li>
        <li><a href="${pageContext.request.contextPath}/logout" title="LogOut">LogOut</a></li>
        </c:if>
                </ul>
            </div>
        </div>
    </div>
    <div class="container body-content">
        <h1>Log In</h1>
        <form:form commandName="name" cssClass="form-horizontal" id = "LoginForm">
        <div class="control-group">
                <label class="control-label">E-mail:</label>
                <div class="controls">
                    <form:input cssClass="input-xlarge" path="email" value=""/>
                    <span class="error"><form:errors path="email" /></span>
                </div>
        </div>
       <div class="control-group">
                <label class="control-label">Password:</label>
                <div class="controls">
                    <form:password cssClass="input-xlarge" path="password" value=""/>
                    <span class="error"><form:errors path="password" /></span>
                </div>
            </div>
                <br>
        <div class="form-actions">
        <tr><td><input type="submit" value="Submit" class="btn btn-primary"></td></tr>
        </div>
    
</form:form>  
        <hr />
        <footer>
        </footer>
    </div>
    </body>
</html>
