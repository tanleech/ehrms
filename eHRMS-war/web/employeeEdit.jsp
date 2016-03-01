<%-- 
    Document   : employeelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.TitleDTO" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
       <script>
         $(document).ready(function () {
             $('#dateJoin').datepicker(); 
             $('#probDue').datepicker(); 
             $('#saveBtn').click(function ()
             {
                $('#action').val('A'); 
                $('#myForm').submit();   
             }
             );
             
         });
       </script>
  </head>
    
  <body class="hold-transition skin-blue sidebar-mini" >
      <!-- header -->
      <%@include file="header.jsp"%>
      <!-- Left side column. contains the logo and sidebar -->
      <%@include file="sidemenu.jsp"%>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <h1>Add Employee
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
                 <form action="employeeEdit" method="post" id="myForm" class="form-horizontal">
                    <input type="hidden" value="" id="action" name="action"/>
                  <span class="content form-control ">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Name</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="name"/>   
                     </div>
                     <label class=" control-label col-sm-2">Email</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="email"/>   
                     </div>
                    </div>
                      
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Department</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="dept" name="dept">
                            <c:choose>
                                <c:when test="${!empty requestScope.deptList}">
                                    <c:forEach var="entry" items="${requestScope.deptList}">
                                        <option value="<c:out value="${entry.id}"/>"><c:out value="${entry.description}"/></option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                          
                     </div>
                     <label class=" control-label col-sm-2">Mobile</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="mobile"/>   
                     </div>
                    </div> 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Job Title</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="title" name="title">
                            <c:choose>
                                <c:when test="${!empty requestScope.titleList}">
                                    <c:forEach var="titleEntry" items="${requestScope.titleList}">
                                        <option value="<c:out value="${titleEntry.id}"/>"><c:out value="${titleEntry.description}"/></option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select> 
                     </div>
                     <label class=" control-label col-sm-2">Office</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="office"/>   
                     </div>
                    </div> 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Reports:</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="mgr" name="mgr">
                            <option value="0">None</option>
                            <c:choose>
                                <c:when test="${!empty requestScope.mgrList}">
                                    <c:forEach var="entry" items="${requestScope.mgrList}">
                                        <option value="<c:out value="${entry.id}"/>"><c:out value="${entry.name}"/></option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                      
                     </div>
                     <label class=" control-label col-sm-2">User Role</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="role" name="role">
                            <c:choose>
                                <c:when test="${!empty requestScope.roleList}">
                                    <c:forEach var="entry" items="${requestScope.roleList}">
                                        <option value="<c:out value="${entry.id}"/>"><c:out value="${entry.description}"/></option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    </div>
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Login</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="login"/>   
                     </div>
                    </div>
                  </span>
                  
                  <span class="content form-control">
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Date Joined</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" id="dateJoin" name="dateJoin"/>   
                     </div>
                     <label class=" control-label col-sm-2">Probation End</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" id="probDue" name="probDue"/>   
                     </div>
                    </div> 
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Base Leave Entitlement</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" id="base" name="base"/>   
                     </div>
                     <label class=" control-label col-sm-2">Max Leave<br/>Entitlement</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="max"/>   
                     </div>
                    </div> 
                    <!--  
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Current Leave<br/>Entitlement</label>
                     <div class="col-sm-3">
                        <input type="text" class="form-control" name="mobile"/>   
                     </div>
                    </div> 
                    -->  
                    <!--     
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Category</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="title">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                        </select>                         
                     </div>
                    </div> 
                    -->
                  </span> 
                 </form>
            <!-- Main content -->
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>
