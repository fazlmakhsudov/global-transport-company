<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="${lang.Sign_up}" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body style="background-color: #666666;">

    <div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100">
                <form class="login100-form validate-form" action="/gtc/controller?command=signup" method="POST">
                    <span class="login100-form-title p-b-43">
                        ${lang.Signup_to_continue}
                    </span>
                    <div class="wrap-input100 validate-input" data-validate="Name is required">
                        <input class="input100" type="text" name="name" placeholder='${lang.Name}'
                            value='${sessionScope.newUser.name}' minlength="4">
                        <span class="focus-input100"></span>

                    </div>

                    <div class="wrap-input100 validate-input" data-validate="Surname is required">
                        <input class="input100" type="text" name="surname" placeholder='${lang.Surname}'
                            value='${sessionScope.newUser.surname}' minlength="3">
                        <span class="focus-input100"></span>

                    </div>

                    <div class="wrap-input100 validate-input" data-validate="Valid email is required: ex@abc.xyz">
                        <input class="input100" type="email" name="email"
                            value='${sessionScope.newUser.email}' placeholder='${lang.Email}'>
                        <span class="focus-input100"></span>

                    </div>


                    <div class="wrap-input100 validate-input" data-validate="Password is required">
                        <input class="input100" type="password" id="password" name="password" minlength="4"
                            placeholder='${lang.Password}'>
                        <span class="focus-input100"></span>

                    </div>


                    <div class="wrap-input100 validate-input" data-validate="Password is required">
                        <input class="input100" type="password" id="repeat-password" name="repeat-password"
                            minlength="4" placeholder='${lang.Repeat_password}'>
                        <span class="focus-input100"></span>

                    </div>
                    <div id='error' class='text-right'></div>
                    <div class="container-login100-form-btn">
                        <button class="login100-form-btn" id='submit-button'>
                            ${lang.sign_up}
                        </button>
                    </div>

                    <div class="text-center p-t-46 p-b-20">
                        <span class="txt2">
                            or <a href="/gtc/controller?command=login">${lang.Login}</a>
                        </span>
                    </div>
                    <div class='text-center h5 text-danger'>
                        ${sessionScope.errorSignUp}
                    </div>
                    <c:remove var="errorSignUp" />
                    <c:remove var="newUser" />
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