<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.DeptDTO" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     
 <script>
         $(document).ready(function () {
             $('#saveBtn').click(function ()
             {
                 //alert('submit');
                 $('#action').val('A');
                 $('#myForm').submit();
                 //window.location.href="roleList";
             }      
             );
         }
          );
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
            <h1>Add Role
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
        <form action="roleEdit" method="post" id="myForm" class="form-horizontal ">
            <input type="hidden" value="" id="action" name="action"/>
            <div class="form-group">
                     <label class=" control-label col-sm-2">Name</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="name" />
                     </div>
            </div>
            <div class="form-group">
                     <label class=" control-label col-sm-2">System Access</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="system" name="system">
                           <option value="0">Inactive</option>
                           <option value="1">Active</option>
                        </select>
                     </div>
            </div>
            <c:forEach var="entry" items="${sessionScope.moduleList}">
            <div class="form-group">
                <c:if test="${entry.name == 'Email Notification'}">
                     <label class=" control-label col-sm-2">Email Notification</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="email" name="${entry.name}">
                           <option value="0">No</option>
                           <option value="1">Yes</option>
                        </select>
                     </div>
                </c:if>
                <c:if test="${entry.name != 'Email Notification'}">
                     
                <label class=" control-label col-sm-2"><c:out value="${entry.name}"/></label>
                     <div class="col-sm-3">
                            <select class="form-control" name="${entry.name}">
                               <option value="0">No Access</option>
                               <option value="1">View</option>
                               <option value="2">View & Edit</option>
                            </select>
                     </div>
                </c:if>               
            </div>                     
            </c:forEach>
        </form>
            <!-- Main content -->
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

