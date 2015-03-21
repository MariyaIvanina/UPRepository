<%-- 
    Document   : home
    Created on : Mar 14, 2015, 11:32:16 PM
    Author     : Mary
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            body {
                padding-top : 40px !important;
            }

       </style>
       <link href="<c:url value="${theme}"/>" rel="stylesheet" type="text/css">
        <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>  
        <title>Welcome</title>
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
        <li><a href="${pageContext.request.contextPath}/chat" title ="User" >${currentuser}</a></li>
        <li><a href="${pageContext.request.contextPath}/logout" title="LogOut"><spring:message code="label.logout"/></a></li>
        </c:if>
                </ul>
            </div>
        </div>
    </div>
    <div class="container body-content">
        
          <h1><spring:message code="label.home"/></h1>
        <table>
        <h2></h2>
       <tr><td><a href="${pageContext.request.contextPath}/index" title="Users"><spring:message code="label.users"/></a></td></tr>
       <tr> <span style="float: right">
    <a href="?lang=en">en</a> 
    | 
    <a href="?lang=ru">ru</a>
       </span>
        
        </tr>
        </table>

        <hr />
        <footer>
        </footer>
    </div>
    
    </body>
</html>

