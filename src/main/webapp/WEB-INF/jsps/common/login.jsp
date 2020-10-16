<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="Login" />
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
				<form class="login100-form validate-form" action="/gtc/controller?command=login" method="POST">
					<span class="login100-form-title p-b-43">
						Login to continue
					</span>


					<div class="wrap-input100 validate-input" data-validate="Valid email is required: ex@abc.xyz">
						<input class="input100" type="email" name="email" value='${email}'>
						<span class="focus-input100"></span>
						<span class="label-input100">Email</span>
					</div>


					<div class="wrap-input100 validate-input" data-validate="Password is required">
						<input class="input100" type="password" name="password" minlength="4">
						<span class="focus-input100"></span>
						<span class="label-input100">Password</span>
					</div>

					<div class="flex-sb-m w-full p-t-3 p-b-32">
						<div>
							<a href="#" class="txt1">
								Forgot Password?
							</a>
						</div>
					</div>


					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							Login
						</button>
					</div>

					<div class="text-center p-t-46 p-b-20">
						<span class="txt2">
							or <a href="/gtc/controller?command=signup">sign up</a>
						</span>
					</div>
                    <div class='text-center h5 text-danger'>
                        ${errorSignIn}
                    </div>
				</form>

				<div class="login100-more" style="background-image: url('images/bg-01.jpg');">
				</div>
			</div>
		</div>
	</div>



	<%@ include file="/WEB-INF/jspf/footer.jspf"%>


</body>

</html>