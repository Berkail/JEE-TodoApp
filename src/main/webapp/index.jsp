<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Home</title>
<link rel="stylesheet" href="css/home.css">
</head>
<body>
	<div class="container" id="container">

		<!-- this is the sign up form -->
		<div class="form-container sign-up-container">
			<form action="Signup" method="post">
				<h1>Create Account</h1>
				<div th:if="${error}" style="color: red;">
					<p>${error}</p>
				</div>
				<input type="text" placeholder="Username" name="username" required/> <input
					type="email" placeholder="Email" name="email" required/> <input
					type="password" placeholder="Password" name="password" required/> <input
					type="password" placeholder="Confirm Password" name="confPassword" required/>
				<button>Sign Up</button>
			</form>
		</div>

		<!-- this is the sign in form -->
		<div class="form-container sign-in-container">
			<form action="Login" method="post">
				<h1>Login</h1>
				<input type="email" placeholder="Email" name="email" required/> <input
					type="password" placeholder="Password" name="password" required/>
				<button>Login</button>
			</form>
		</div>

		<div class="overlay-container">
			<div class="overlay">
				<div class="overlay-panel overlay-left">
					<h1>Welcome Back!</h1>
					<p>To keep connected with us please login with your personal
						info</p>
					<button class="ghost" id="signIn">Sign In</button>
				</div>
				<div class="overlay-panel overlay-right">
					<h1>Hello, Friend!</h1>
					<p>Enter your personal details and start journey with us</p>
					<button class="ghost" id="signUp">Sign Up</button>
				</div>
			</div>
		</div>
	</div>
	<script src="js/home.js"></script>
</body>
</html>