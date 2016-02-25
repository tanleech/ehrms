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
             var indx=0;
             $('#action').val('');
             $('#addBtn').click(function ()
             {
                 //alert('submit');
                 //$('#myForm').submit();
                 var row = '<tr><td width="40%"><select class="form-control" name="employee" id="emp'+indx+'"><c:forEach var="entry" items="${requestScope.usrList}"><option value="<c:out value="${entry.id}"/>"><c:out value="${entry.name}"/></option></c:forEach></select></td>'+
                         '<td class="pull-right"><a href="#"  class="del"><span class="glyphicon glyphicon-remove"/></a></td></tr>';
                 $('#deptTab').append(row);
                 indx++;
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
            <h1>Add Department - Edit
                <button type="button" class="btn btn-primary pull-right" id="saveBtn">Save</button>
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
                    <div class="form-group">
                     <label class=" control-label col-sm-1">Name</label>
                     <div class="col-sm-3">
                         <input type="text" class="form-control" name="name" readonly
                                value="<c:out value='${requestScope.dept}'/>"/>
                     </div>
                    </div>
                    <div class="form-group">  
                     <label class=" control-label col-sm-1">Manager</label>
                     <div class="col-sm-3">
                        <select class="form-control" id="mgr" name="manager">
                            <c:choose>
                                <c:when test="${!empty requestScope.usrList}">
                                    <c:forEach var="entry" items="${requestScope.usrList}">
                                        <option value="<c:out value="${entry.id}"/>"><c:out value="${entry.name}"/></option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>                         
                     </div>
                    </div>
                <table id="deptTab" class="table table-bordered table-hover">
                    <thead>
                      <tr> 
                          <th colspan="2">Name</th>
                      </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                     
                </span> 
                     <br/>
                    <div class="col-sm-2">
                        <button type="button" class="btn btn-primary pull-left" id="addBtn">Add</button>
                    </div>                     
                     
                 </form>
            <!-- Main content -->
               </div>
      </div>
      <%@include file="footer.jsp" %>
  </body>      



</html>

