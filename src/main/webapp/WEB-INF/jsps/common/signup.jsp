<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="Sign up" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body style="background-color: #666666;">
    <!-- header -->
       <header>
          <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
       </header>
    <!-- header -->
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form validate-form" action="/gtc/controller?command=signup" method="POST">
					<span class="login100-form-title p-b-43">
						Signup to continue
					</span>
					<div class="wrap-input100 validate-input" data-validate="Name is required">
						<input class="input100" type="text" name="name" minlength="4">
						<span class="focus-input100"></span>
						<span class="label-input100">First name</span>
					</div>

					<div class="wrap-input100 validate-input" data-validate="Surname is required">
						<input class="input100" type="text" name="surname" minlength="3">
						<span class="focus-input100"></span>
						<span class="label-input100">Second name</span>
					</div>

					<div class="wrap-input100 validate-input" data-validate="Valid email is required: ex@abc.xyz">
						<input class="input100" type="email" name="email">
						<span class="focus-input100"></span>
						<span class="label-input100">Email</span>
					</div>


					<div class="wrap-input100 validate-input" data-validate="Password is required">
						<input class="input100" type="password" id="password" name="password" minlength="4">
						<span class="focus-input100"></span>
						<span class="label-input100">Password</span>
					</div>


					<div class="wrap-input100 validate-input" data-validate="Password is required">
						<input class="input100" type="password" id="repeat-password" name="repeat-password"
							minlength="4">
						<span class="focus-input100"></span>
						<span class="label-input100">Repeat password</span>
					</div>
					<div id='error' class='text-right'></div>
					<div class="container-login100-form-btn">
						<button class="login100-form-btn" id='submit-button'>
							Sign up
						</button>
					</div>

					<div class="text-center p-t-46 p-b-20">
						<span class="txt2">
							or <a href="/gtc/controller?command=login">login</a>
						</span>
					</div>

				</form>
				<div class="login100-more" style="background-image: url('images/bg-02.jpg');">
				</div>
			</div>
		</div>
		<p id="test"></p>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html>