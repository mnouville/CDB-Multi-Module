<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/"/>"><spring:message code="application.title"/></a>
            <a class="navbar-brand" style="float:right" href="<c:url value="/logout"/>"><spring:message code="application.disconnect"/></a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${idcomputer}" />
                    </div>
                    <h1><spring:message code="editComputer.title"/></h1>

                    <form action="<c:url value="/EditComputer"/>" method="POST">
                        <input type="hidden" value="<c:out value="${idcomputer}"/>" id="id" name="id"/> 
                        <fieldset>
                            <div class="form-group">
                                <label for="name"><spring:message code="editComputer.name"/></label>
                                <input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="editComputer.namePlaceHolder"/>" value="<c:out value="${name}"/>">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="editComputer.introduced"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="<c:out value="${introduced}"/>">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="editComputer.discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="<c:out value="${discontinued}"/>">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="editComputer.company"/></label>
                                <select class="form-control" id="companyId" name="companyid">
                                    <option value="0">--</option>
                                    <c:forEach  items="${companies}" var="c" >
                                    	<c:choose>
	                                    	<c:when test = "${c.id == companyid }">
									            <option selected value="<c:out value='${c.id}' />"><c:out value="${c.name}" /></option>
									        </c:when>
									        <c:otherwise>
									            <option value="<c:out value='${c.id}' />"><c:out value="${c.name}" /></option>
									        </c:otherwise>
                                    	</c:choose>
				                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="editComputer.editButton"/>" class="btn btn-primary">
                            	<spring:message code="application.or"/>
                            <a href="<c:url value="/"/>" class="btn btn-default"><spring:message code="application.cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/dashboard.js"></script>
</body>

</html>