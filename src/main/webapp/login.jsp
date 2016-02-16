<%-- 
    Document   : login
    Created on : Feb 5, 2016, 5:30:55 AM
    Author     : ahsan
--%>
<% 
    String login = (String) request.getSession().getAttribute("loggedin");
    try{
        if(login.equals("proceed")){
        response.sendRedirect("dashboard");
    }
    }catch(Exception ex){
        
    }
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<meta name="description" content="Everest Admin Panel" />
		<meta name="keywords" content="Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap" />
		<meta name="author" content="Bootstrap Gallery" />
		<link rel="shortcut icon" href="img/favicon.ico">
		<title>Apisylux</title>
		
		<!-- Bootstrap CSS -->
		<link href="css/bootstrap.css" rel="stylesheet" media="screen">

		<!-- Animate CSS -->
		<link href="css/animate.css" rel="stylesheet" media="screen">

		<!-- Main CSS -->
		<link href="css/main.css" rel="stylesheet" media="screen">

		<!-- Main CSS -->
		<link href="css/login.css" rel="stylesheet">

		<!-- Font Awesome -->
		<link href="fonts/font-awesome.min.css" rel="stylesheet">

		<!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="js/html5shiv.js"></script>
			<script src="js/respond.min.js"></script>
		<![endif]-->

	</head>  

	<body>
		<!-- Container Fluid starts -->
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-push-4 col-md-4 col-sm-push-3 col-sm-6 col-sx-12">
					
					<!-- Header end -->
					<div class="login-container">
						<div class="login-wrapper animated flipInY">
							<div id="login" class="show">
								<div class="login-header">
									<h4>Sign In To Your Account</h4>
								</div>
								<form action="signin" name="login">
									<div class="form-group has-feedback">
										<label class="control-label" for="userName">User Name</label>
                                                                                <input type="text" class="form-control" name="username" id="userName" placeholder="User Name" autofocus>
										<i class="fa fa-user text-info form-control-feedback"></i>
									</div>
									<div class="form-group has-feedback">
										<label class="control-label" for="passWord">Password</label>
										<input type="password" class="form-control" id="passWord" placeholder="*********" >
										<i class="fa fa-key text-danger form-control-feedback"></i>
									</div>
									<input type="submit" value="Login" class="btn btn-danger btn-lg btn-block">
								</form>
								<a href="#forgot-pwd" class="underline text-info">Forgot Password?</a>
								<a href="#register">Don't have an account? <span class="text-danger">Sign Up</span></a>
							</div>

							<div id="register" class="form-action hide">
								<div class="login-header">
									<h4>Sign Up for Everest</h4>
								</div>
                                                            <form action="signin?user=signup" name="signup">
									<div class="form-group has-feedback">
										<label class="control-label" for="userName1">User Name</label>
										<input type="text" class="form-control" id="userName1">
										<i class="fa fa-user form-control-feedback"></i>
									</div>
									<div class="form-group has-feedback">
										<label class="control-label" for="password1">Password</label>
										<input type="text" class="form-control" id="password1">
										<i class="fa fa-key form-control-feedback"></i>
									</div>
									<div class="form-group has-feedback">
										<label class="control-label" for="password2">Confirm password</label>
										<input type="text" class="form-control" id="password2">
										<i class="fa fa-key form-control-feedback"></i>
									</div>
									<input type="submit" value="Sign Up" class="btn btn-danger btn-lg btn-block">
								</form>
								<a href="#login">Already have an account? <span class="text-danger">Sign In</span></a>
							</div>

							<div id="forgot-pwd" class="form-action hide">
								<div class="login-header">
									<h4>Reset your Password</h4>
								</div>
								<form action="dashboard">
									<div class="form-group has-feedback">
										<label class="control-label" for="password3">Password</label>
										<input type="text" class="form-control" id="password3">
										<i class="fa fa-key form-control-feedback"></i>
									</div>
									<div class="form-group has-feedback">
										<label class="control-label" for="password4">Confirm password</label>
										<input type="text" class="form-control" id="password4">
										<i class="fa fa-key form-control-feedback"></i>
									</div>
									<input type="submit" value="Reset" class="btn btn-danger btn-lg btn-block">
								</form>
								<a href="#register">Don't have an account? <span class="text-danger">Sign Up</span></a>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Container Fluid ends -->
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="js/jquery.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="js/bootstrap.min.js"></script>

		<script type="text/javascript">
			(function($) {
				// constants
				var SHOW_CLASS = 'show',
					HIDE_CLASS = 'hide',
					ACTIVE_CLASS = 'active';
				
				$('a').on('click', function(e){
					e.preventDefault();
					var a = $(this),
					href = a.attr('href');
				
					$('.active').removeClass(ACTIVE_CLASS);
					a.addClass(ACTIVE_CLASS);

					$('.show')
					.removeClass(SHOW_CLASS)
					.addClass(HIDE_CLASS)
					.hide();
					
					$(href)
					.removeClass(HIDE_CLASS)
					.addClass(SHOW_CLASS)
					.hide()
					.fadeIn(550);
				});
			})(jQuery);
		</script>
	</body>

<!-- Mirrored from jesus.gallery/everest/login.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Apr 2015 10:45:01 GMT -->
</html>