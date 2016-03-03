<%-- 
    Document   : employeelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            $('#addBtn').click(function ()
             {
                 //alert('add');
                 //$('#myForm').submit();
                 var row = '<tr><td width="40%"><select class="form-control" name="employee" id="emp'+indx+'"><c:forEach var="entry" items="${requestScope.usrList}"><option value="<c:out value="${entry.id}"/>"><c:out value="${entry.name}"/></option></c:forEach></select></td>'+
                         '<td class="pull-right"><a href="#"  class="del"><span class="glyphicon glyphicon-remove"/></a></td></tr>';
                 $('#entTab').append(row);
                 indx++;
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
            <h1>
                 Employee Details
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
            </h1>    
        </div>
        <br/>
        <ul class="nav nav-tabs">
          <li class="active"><a data-toggle="tab" href="#tab_1">Primary</a></li>
          <li><a data-toggle="tab" href="#tab_2">Leave</a></li>
        </ul>
        <!-- Main content -->
        <form action="employeeEdit" method="post" id="myForm" class="form-horizontal">
        
        <div class="tab-content ">

             <div id="tab_1" class="tab-pane fade in active">      
                     <input type="hidden" value="" id="action" name="action"/>
                        <span class="content form-control ">
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Name</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="name"
                                     value="${requestScope.user.name}"/>   
                           </div>
                           <label class=" control-label col-sm-2">Email</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="email"
                                     value="${requestScope.user.email}"/>   
                           </div>
                          </div>

                          <div class="form-group">
                           <label class=" control-label col-sm-1">Department</label>
                           <div class="col-sm-3">
                              <select class="form-control" id="dept" name="dept">
                                  <c:choose>
                                      <c:when test="${!empty requestScope.deptList}">
                                          <c:forEach var="entry" items="${requestScope.deptList}">
                                              <option value="${entry.id}"
                                                      ${requestScope.user.dept.dept.id == entry.id ? 'selected' : ''}>
                                                  ${entry.description}
                                              </option>
                                          </c:forEach>
                                      </c:when>
                                  </c:choose>
                              </select>                          
                           </div>
                           <label class=" control-label col-sm-2">Mobile</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="mobile"
                                     value="${requestScope.user.phone}"/>   
                           </div>
                          </div> 
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Job Title</label>
                           <div class="col-sm-3">
                              <select class="form-control" id="title" name="title">
                                  <c:choose>
                                      <c:when test="${!empty requestScope.titleList}">
                                          <c:forEach var="titleEntry" items="${requestScope.titleList}">
                                              <option value="${titleEntry.id}"
                                                      ${requestScope.user.title.id == titleEntry.id ? 'selected' : ''}>
                                                  ${titleEntry.description}
                                              </option>
                                          </c:forEach>
                                      </c:when>
                                  </c:choose>
                              </select> 
                           </div>
                           <label class=" control-label col-sm-2">Office</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="office"
                                     value="${requestScope.user.office}"/>   
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
                                              <option value="${entry.id}"  
                                                       ${requestScope.user.id == entry.id ? 'selected' : ''}>
                                              ${entry.name}
                                              </option>
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
                                              <option value="${entry.id}"
                                                       ${requestScope.user.role.role.id == entry.id ? 'selected' : ''}>
                                                  ${entry.description}
                                              </option>
                                          </c:forEach>
                                      </c:when>
                                  </c:choose>
                              </select>                         
                           </div>
                          </div>
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Login</label>
                           <div class="col-sm-3">
                               <input type="text" class="form-control" name="login"
                                      value="${requestScope.user.login}"/>   
                           </div>
                          </div>
                        </span>

                        <span class="content form-control">
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Date Joined</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" id="dateJoin" name="dateJoin"
                                     value="${requestScope.user.dateJoin}"/>   
                           </div>
                           <label class=" control-label col-sm-2">Probation End</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" id="probDue" name="probDue"
                                     value="${requestScope.user.probationDue}"/>   
                           </div>
                          </div> 
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Base Leave Entitlement</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" id="base" name="base"
                                     value="${requestScope.entAnnual.current}"/>   
                           </div>
                           <label class=" control-label col-sm-2">Max Leave<br/>Entitlement</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="max"
                                     value="${requestScope.entAnnual.max}"/>   
                           </div>
                          </div> 

                          <div class="form-group">
                           <label class=" control-label col-sm-1">Current Leave<br/>Balance</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="balance"
                                     value="${requestScope.entAnnual.balance}"/>   
                           </div>
                          </div> 
                        </span> 
                      </div>     
                      <div id="tab_2" class="tab-pane fade">
                        <span class="content">
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Annual Accrued</label>
                           <div class="col-sm-3">
                              <fmt:formatNumber type="number" maxFractionDigits="1" value="${requestScope.accured}" var="accrued"/>
                              <input type="text" class="form-control" name="annualAccrued"
                                     value="${accrued}"/>   
                           </div>
                           <label class=" control-label col-sm-2">Carried Over</label>
                           <div class="col-sm-3">
                               
                              <input type="text" class="form-control" name="cf"
                                     value="${requestScope.entAnnual.carriedOver}"/>   
                           </div>
                          </div>
                          <div class="form-group">
                           <label class=" control-label col-sm-1">Total Balance</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="bal"
                                     value="${requestScope.entAnnual.balance}"/>
                           </div>
                           <label class=" control-label col-sm-2">Computed CF(25%)</label>
                           <div class="col-sm-3">
                              <input type="text" class="form-control" name="computedCF"
                                     value="${requestScope.entAnnual.carriedOver}"/>   
                           </div>
                          </div> 
                <table id="entTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                        <th>Leave Type</th>
                        <th>No of days</th>
                        <th>Delete</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                            <c:choose>
                                <c:when test="${!empty requestScope.entList}">
                                    <c:forEach var="entry" items="${requestScope.entList}">
                                        <c:if test="${entry.leaveType.description eq 'Annual'}">
                                        <tr>
                                            <td>
                                              ${entry.leaveType.description}
                                            </td>
                                            <td>
                                                ${entry.current}
                                            </td>
                                            <td>
                                              <a href="employeeEdit?action=D&entId=${entry.id}&userId=${requestScope.user.id}"  class="del" id="delBtn">
                                                <span class="glyphicon glyphicon-remove"/>
                                              </a>
                                            </td>
                                            
                                        </tr>
                                            
                                        </c:if>
                                        
                                    </c:forEach>
                                </c:when>
                            </c:choose>                        
                    </tbody>
                </table>
                           <button type="button" class="btn btn-primary pull-left" id="addBtn">Add</button>
               </span>
            <!-- Main content -->
        </div> 
      </form>                               
      </div>
  </body>      

 <%@include file="footer.jsp" %>

</html>
