<%-- 
    Document   : chat
    Created on : Mar 14, 2015, 11:31:25 PM
    Author     : Mary
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
       <link href="<c:url value="${theme}"/>" rel="stylesheet" type="text/css">
       <link href="<c:url value="/resources/css/mycss.css"/>" rel="stylesheet" type="text/css">
        <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>  
        <script src="<c:url value="/resources/scripts/script.js"/>" type="text/javascript"></script>  
        <script type="text/javascript">starting();</script>
        <script type="text/javascript">startingUsers();</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat</title>
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
                <c:if test="${username == null}">
                   <li><a href="${pageContext.request.contextPath}/login" title ="LogIn" id="LoginPopup"><spring:message code="label.login"/></a></li>
                   <li><a href="${pageContext.request.contextPath}/registration" title="Registration"><spring:message code="label.registration"/></a></li>
        </c:if>
        <c:if test="${username != null}">
        <li><a href="${pageContext.request.contextPath}/chat" title ="User" id="NickName1">${username}</a></li>
        <li><a href="${pageContext.request.contextPath}/logout" title="LogOut"><spring:message code="label.logout"/></a></li>
        </c:if>
                </ul>
            </div>
        </div>
    </div>
    <div class="container body-content">
    <input id="kol" type="hidden" value="0"/> 
    <input id="nick" type="hidden" value="${username}"/>
    <input id="nickID" type="hidden" value="${nickID}"/>
    <div class="container jumbotron" style="margin-top: 40px">
        <div class="row">
	<div class="col-md-2">
                 <img src="http://vladsokolovsky.com/forums/uploads/profile/photo-36.jpg?_r=0" alt="bootstrap Chat box user image" class="img-circle" />
            </div>
            <div class="col-md-4" id="forName">
                <h4>${username}</h4>
                <button class="btn" id="editNick" onclick="editNick();"><spring:message code="label.editnick"/></button>
            </div>
        </div>
        <br>
        <br>
         <button type="button" class="btn btn-primary btn-success" id="state" onclick="changeState(false);">Server is available</button>
        <br>
        <div class="row">
        <div class=" col-lg-6 col-md-6 col-sm-6">
                <div class="chat-box-div" style="height:500px">
                    <div class="chat-box-head">
                        GROUP CHAT HISTORY
                    </div>
                    <div class="panel-body chat-box-main" id="myChat">
                    	
                    </div>
                    <div class="chat-box-footer">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Enter Text Here..." id="message">
                            <span class="input-group-btn">
                                <button onclick="sendClick();" class="btn btn-info" id="send"><spring:message code="label.send"/></button>
                            </span>
                        </div>
                    </div>

                </div>

            </div>
            <div class="col-lg-3 col-md-3 col-sm-3">
                <div class="chat-box-online-div" style="height:500px">
                    <div class="chat-box-online-head" id="info">
                        ONLINE USERS (0)
                    </div>
                    <div class="panel-body chat-box-online" id="users">
                    </div>

                </div>

            </div>
    </div>
    </body>
</html>
