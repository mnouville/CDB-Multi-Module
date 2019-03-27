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
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	<c:set var="sort" value="${param['sort']}" scope="application"></c:set>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value="/"/>"><spring:message code="application.title"/></a>
            <a class="navbar-brand" style="float:right" href="<c:url value="/logout"/>"><spring:message code="application.disconnect"/></a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
            	<spring:message code="dashboard.computersFound" arguments="${maxcomputer}"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="<c:url value="/Search"/>" method="POST" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="dashboard.searchName"/>" />
                        <input type="submit" id="searchsubmit" value="<spring:message code="dashboard.searchButton"/>" class="btn btn-primary" />
                    </form>
                </div>
                <c:choose>
				  <c:when test="${user.role == 1}">
				    <div class="pull-right">
	                    <a class="btn btn-success" id="addComputer" href="<c:url value="/AddComputer"/>"><spring:message code="dashboard.addComputerButton"/></a> 
	                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.editComputerButton"/></a>
	                </div>
				  </c:when>
				</c:choose>
                
            </div>
        </div>

        <form id="deleteForm" action="<c:url value="/Delete"/>" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th style="width:30%;">
                        	<c:choose>
				            	<c:when test="${param['type'] == null}">
									<a href="Sort?page=${param['page']}&type=ASC&sort=name"><spring:message code="dashboard.computerNameColumn"/></a>
								</c:when>
								<c:otherwise>
									<a href="Sort?page=${param['page']}&type=${type}&sort=name"><spring:message code="dashboard.computerNameColumn"/></a>
								</c:otherwise>
			            	</c:choose>
                        </th>
                        <th>
                        	<c:choose>
				            	<c:when test="${param['type'] == null}">
									<a href="Sort?page=${param['page']}&type=ASC&sort=introduced"><spring:message code="dashboard.introducedColumn"/></a>
								</c:when>
								<c:otherwise>
									<a href="Sort?page=${param['page']}&type=${type}&sort=introduced"><spring:message code="dashboard.introducedColumn"/></a>
								</c:otherwise>
			            	</c:choose>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<c:choose>
				            	<c:when test="${param['type'] == null}">
									<a href="Sort?page=${param['page']}&type=ASC&sort=discontinued"><spring:message code="dashboard.discontinuedColumn"/></a>
								</c:when>
								<c:otherwise>
									<a href="Sort?page=${param['page']}&type=${type}&sort=discontinued"><spring:message code="dashboard.discontinuedColumn"/></a>
								</c:otherwise>
			            	</c:choose>
                        </th>
                        <!-- Table header for Company -->
                        <th style="width:30%;">
                           	<c:choose>
				            	<c:when test="${param['type'] == null}">
									<a href="Sort?page=${param['page']}&type=ASC&sort=company"><spring:message code="dashboard.companyColumn"/></a>
								</c:when>
								<c:otherwise>
									<a href="Sort?page=${param['page']}&type=${type}&sort=company"><spring:message code="dashboard.companyColumn"/></a>
								</c:otherwise>
			            	</c:choose>
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                   <c:forEach  items="${computers}" var="c" >
                        <tr>
                        	<c:choose>
							  <c:when test="${user.role == 1}">
							      <td class="editMode">
                            		  <input type="checkbox" name="cb" class="cb" value="<c:out value="${c.id}"/>">
		                          </td>
		                          <td>
		                              <a href="<c:url value="/EditComputer"/>?id=<c:out value="${c.id}"/>" onclick=""><c:out value="${c.name}" /></a>
		                          </td>
							  </c:when>
							  <c:otherwise>
		                          <td>
		                              <a><c:out value="${c.name}" /></a>
		                          </td>
							  </c:otherwise>
							</c:choose>
                        	
                            <td><c:out value="${c.introduced}" /></td>
                            <td><c:out value="${c.discontinued}" /></td>
                            <td><c:out value="${c.companyname}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="<c:url value="/"/>?page=1" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                	</a>
	            </li>
	            <c:set var="page" value="0" />
	            <c:forEach var="i" begin="0" end="${maxcomputer}" step="1">
	            	<c:choose>
		            	<c:when test="${i % 50 == 0}">
		            		<c:set var="page" value="${page + 1}" scope="page"/>
		            		<c:choose>
		            			<c:when test="${sort == 'name'}">
		            				<li><a href="Sort?page=<c:out value='${page}'/>&type=<c:out value="${param['type']}"/>&sort=name"><c:out value="${page}"/></a></li>
		            			</c:when>
		            			<c:when test="${sort == 'intro'}">
		            				<li><a href="Sort?page=<c:out value='${page}'/>&type=<c:out value="${param['type']}"/>&sort=introduced"><c:out value="${page}"/></a></li>
		            			</c:when>
		            			<c:when test="${sort == 'disc'}">
		            				<li><a href="Sort?page=<c:out value='${page}'/>&type=<c:out value="${param['type']}"/>&sort=discontinued"><c:out value="${page}"/></a></li>
		            			</c:when>
		            			<c:when test="${sort == 'company'}">
		            				<li><a href="Sort?page=<c:out value='${page}'/>&type=<c:out value="${param['type']}"/>&sort=company"><c:out value="${page}"/></a></li>
		            			</c:when>
		            			<c:otherwise>
		            				<li><a href="<c:url value="/"/>?page=<c:out value='${page}'/>"><c:out value="${page}"/></a></li>
		            			</c:otherwise>	
		            		</c:choose>
						</c:when>
	            	</c:choose>
				</c:forEach>
	            <li>
	            	<a href="<c:url value="/"/>?page=<c:out value='${page}'/>" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
	            </li>
        	</ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>
    </footer>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/dashboard.js"></script>
</body>
</html>