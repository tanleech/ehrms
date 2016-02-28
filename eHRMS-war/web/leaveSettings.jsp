<%-- 
    Document   : addDept
    Created on : Feb 15, 2016, 1:26:00 PM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.List" %>
<%@ page import="com.sapuraglobal.hrms.dto.DeptDTO" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="head.jsp"%>
     
 <script>
         $(document).ready(function () {
             $('#addBtn').click(function ()
             {
                 
             }
             );
             $('#saveBtn').click( function()
                {
                    //alert('save btn clicked');
                    var i;
                    var emp = '{"employee":[';//+mgr+'"}]}';
                    for(i=0;i<indx;i++)
                    {
                         var selected = $('#emp'+i+' option:selected').val();
                         //alert('i: '+selected);
                         emp = emp + '{"id":"'+ selected +'"}';
                         if(i<indx-1)
                         {
                             emp = emp+',';
                         }
                    }
                    emp = emp+"]}";
                    //alert('emp: '+emp)
                    //alert('emp: '+JSON.stringify(JSON.parse(emp)));
                    var mgr = $('#mgr option:selected').val();
                    mgr = '{"mgr":[{"id":"'+mgr+'"}]}';
                    $('#manager').val(mgr);
                    $('#emp').val(emp);
                    $('#action').val('A');
                    //alert('Manager: '+JSON.stringify(JSON.parse(mgr)));
                    $('#myForm').submit();
                }
             );
             $('#deptTab').on('click','.del',function()
                {
                  $(this).closest( 'tr').remove();
                }      
             );
             $('#deptTab').DataTable({
                        "paging": false,
                        "lengthChange": false,
                        "searching": false,
                        "ordering": false,
                        "info": true,
                        "autoWidth": true
                      });
     
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
                    Leave Settings
                <button type="button" class="btn btn-primary pull-right" id="addBtn">Add</button>
            </h1>    
        </div>
        <br/>
        <!-- Main content -->
               <div class="box-body">
                  <form action="deptEdit" method="post" id="myForm" class="form-horizontal">
                      <input type="hidden" value="" id="manager"  name="mgr"/>
                      <input type="hidden" value="" id="emp" name="emp"/>
                      <input type="hidden" value="" id="action" name="action"/>
                      
                    <span class="content form-control" id="panel" style="height: 100%">
                    <!--    
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Yearly Increment</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="name" 
                                value="<c:out value='${requestScope.dept}'/>"/>
                     </div>
                     <label class=" control-label col-sm-2">Carry Over %</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="name" 
                                value="<c:out value='${requestScope.dept}'/>"/>
                     </div>
                    </div>
                    -->
                <table id="deptTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                          <th>Type</th>
                          <th>No of Days</th> 
                          <th>Option</th> 
                          <th>Delete</th> 
                          
                      </tr>
                    </thead>
                    <tbody>
                         <c:forEach var="entry" items="${requestScope.leaveTypelist}" varStatus="loop">
                          <tr>   
                            <td width="40%">
                                <a href ="#" ><c:out value='${entry.description}'/></a>
                            </td>
                            <td width="20%">
                                <c:out value='${entry.days}'/>
                            </td>
                            <td width="30%">
                                    <c:if test="${entry.mandatory eq 'Y'}">
                                        Default
                                    </c:if>
                                    <c:if test="${entry.mandatory eq 'N'}">
                                        Optional
                                    </c:if>
                            </td>
                            <td width="10%">
                                 <a href="#"  class="del"><span class="glyphicon glyphicon-remove"/></a>                               
                            </td>
                          </tr> 
                        </c:forEach>
                    </tbody>
                </table>
                     
                </span> 
                   <!--                  
                     <br/>
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-primary pull-left" id="addBtn">Add</button>
                    </div>                     
                     -->
                 </form>
            <!-- Main content -->
               </div>
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

