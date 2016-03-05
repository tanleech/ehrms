<%-- 
    Document   : employeelist
    Created on : Feb 3, 2016, 3:17:10 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.UserDTO" %>


<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     <script>
         $(document).ready(function () {
             $('#Apply').click(function ()
             {
                window.location.href="employeeEdit"; 
             }      
             );
             $('#leaveTab').DataTable({
                        "paging": true,
                        "lengthChange": false,
                        "searching": true,
                        "ordering": true,
                        "info": true,
                        "autoWidth": true,
                        "pageLength": 7
                      });    
             
          }
          );
     </script>
  </head>


    
  <body class="hold-transition skin-blue sidebar-mini">
      <!-- header -->
      <%@include file="header.jsp"%>
      <!-- Left side column. contains the logo and sidebar -->
      <%@include file="sidemenu.jsp"%>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>Leave  
                <button type="button" class="btn btn-primary pull-right" id="Apply">Apply</button>
            </h1>    
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
                
              <div class="box">
                <div class="box-header">
                </div><!-- /.box-header -->
                <div class="box-body" id="tab">
                    <table id="leaveTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                          <th>Employee</th>
                          <th>Start</th> 
                          <th>End</th> 
                          <th>Days</th> 
                          <th>Type</th> 
                          <th>Submitted</th> 
                          <th>Manager</th> 
                          <th>Status</th> 
                          <th>Action</th> 
                      </tr>
                    </thead>
                    <tbody>
                         <c:forEach var="entry" items="${requestScope.leaveTxnlist}">
                          <tr>   
                            <td width="10%">
                                ${entry.user.name}
                            </td>
                            <td width="10%">
                                ${entry.start}-${entry.start_slot}
                            </td>
                            <td width="10%">
                                ${entry.end}-${entry.end_slot}
                            </td>
                            <td width="10%">
                                ${entry.days}
                            </td>
                            <td width="10%">
                                ${entry.leaveType.description}
                            </td>
                            <td width="10%">
                                ${entry.created}
                            </td>
                            <td width="10%">
                                ${entry.user.approver}
                            </td>
                            <td width="10%">
                                ${entry.status.description}
                            </td>
                            <td width="20%">
                                <a href="#" >Approve</a>
                                <a href="#" >Reject</a>
                            </td>
                            
                          </tr> 
                        </c:forEach>
                    </tbody>
                </table>

                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
        <!-- Main content -->
        </section>  
      </div><!-- /.content-wrapper -->
      <%@include file="footer.jsp" %>
  </body>
</html>
