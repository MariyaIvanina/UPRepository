<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
         <style>
            body {
                padding-top : 40px !important;
            }

       </style>
        <link href="<c:url value="${theme}"/>" rel="stylesheet" type="text/css">
        <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
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
            <h1>Users</h1>
        <table class="table table-striped table-hover">
            
    <!-- column headers -->
    <thead>
        <th>User_id</th>
        <th>User_name</th>
        <th>Email</th>
        <th>Member_since</th>
    </thead>
<!-- column data -->
<tbody>
<c:forEach var="row" items="${Users.getList()}">
    <tr>
        <td><c:out value="${row.getUser_id()}"/></td>
        <td><c:out value="${row.getUser_name()}"/></td>
        <td><c:out value="${row.getEmail()}"/></td>
        <td><c:out value="${row.getMember_since()}"/></td>
    </tr>
</c:forEach>
</tbody>
</table>
    <div class="pager"  style="margin-left:100px">
        ${table}
    </div
   
       <hr />
        <footer>
        </footer>
    </div>
       
         
    </body>
</html>
