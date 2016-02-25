<%-- 
    Document   : Login
    Created on : Feb 2, 2016, 10:22:51 AM
    Author     : sapura-mac-pro-cto-C02PC1MWG3QT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>

<!DOCTYPE html>
<html>
  <head>
    <%@include file="head.jsp"%>
  </head>
    <body class="hold-transition login-page">
    <div class="login-box">
      <div class="login-logo">
          <b>HRMS Login</b>
      </div><!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>
        <form action="login" method="post">

          <div class="form-group has-feedback">
            <input type="text" class="form-control" placeholder="Login" name="loginId" id="login">
          </div>
          <div class="form-group has-feedback">
              <input type="password" class="form-control" id="passwd" name="password"/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
              &nbsp;
            <!--  
            <div class="col-xs-5">
              <div class="checkbox icheck">
                <label>
                    <input type="checkbox"> Remember Me</input>
                </label>
              </div>
            </div>
            -->
            <!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
            </div><!-- /.col -->
          </div>
        </form>

      <!--  <a href="#">I forgot my password</a><br> -->

      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->

    <!-- jQuery 2.1.4 -->
    <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <!-- iCheck -->
    <script src="plugins/iCheck/icheck.min.js"></script>
    <script>
      $(function () {
        $('#login').attr("placeholder", "Enter Login name");
        $("#passwd").attr("placeholder", "Enter password")
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
        
      });
    </script>
  </body>
</html>