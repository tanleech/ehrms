<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     
 <script>
         $(document).ready(function () {
             $('#apply').click(function ()
             {
                 $('#action').val('AS');
                 $('#myForm').submit();    
             }      
             );
             $('#leaveType').change(function ()
             {
                 var type = $('#leaveType').val();
                $.get("leaveEntAdd?action=T&typeId="+type, function(data, status){
                        //alert("Data: " + data + "\nStatus: " + status);
                        //alert(data);
                        $('#days').val(data);
                        
                    });
             }
             );
             $('#startDate').datepicker();
             $('#endDate').datepicker();
     
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
      <div class="content-wrapper" id="main">
          
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <h1>
                 Apply Leave
                <button type="button" class="btn btn-primary pull-right" id="assignBtn">Submit</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
               <div class="box-body">
                  <form action="#" method="post" id="myForm" class="form-horizontal">
                    <input type="hidden" value="" id="action" name="action"/>
                    <input type="hidden" value="${requestScope.user.login}" id="login" name="login" />
                    <span class="content form-control" id="panel" style="height: 100%">
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Type</label>
                     <div class="input-group col-sm-3">
                        <select class="form-control" id="leaveType" name="leaveType">
                            <option value="0">
                                None
                            </option>    
                            <c:choose>
                                <c:when test="${!empty requestScope.typeList}">
                                    <c:forEach var="entry" items="${requestScope.typeList}">
                                        <option value="${entry.id}">${entry.description}</option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    </div> 
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Start Date</label>
                     <div class="input-group col-sm-3">
                         <input value="" class="form-control pull-right"  id="startDate" name="startDate"/>
                         <div class="input-group-addon">
                                      <i class="fa fa-calendar"></i>
                         </div>
                         <select class="form-control" id="slot" name="slot">
                            <option value="AM">
                                AM
                            </option>    
                            <option value="PM">
                                PM
                            </option>    
                        </select>
                     </div>
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">End Date</label>
                        <div class="input-group col-sm-3">
                           <input type="text" class="form-control pull-right" id="endDate" name="endDate"
                                             value=""/>
                                    <div class="input-group-addon">
                                      <i class="fa fa-calendar"></i>
                                    </div>
                                                    <select class="form-control" id="slot" name="slot">
                            <option value="AM">
                                AM
                            </option>    
                            <option value="PM">
                                PM
                            </option>    
                        </select>
                        </div>   
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Total</label>
                     <div class="input-group col-sm-3">
                         <input value="" name="taken" id="taken"/>
                     </div>
                    </div>                         
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Balance</label>
                     <div class="input-group col-sm-3">
                         <input value="" name="balance" id="balance"/>
                     </div>
                    </div>                         
                        
                    </span> 
                 </form>
            <!-- Main content -->
               </div>
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

