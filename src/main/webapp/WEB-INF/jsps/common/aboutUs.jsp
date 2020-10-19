<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="${lang.About_Us}" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="${lang.About_Us}" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->

    <!-- about -->
    <div class="about-w3l py-sm-5 py-4">
        <div class="container py-xl-5 py-lg-3">
            <h3 class="title mb-lg-5 mb-sm-4 mb-3">
                ${lang.About}
                ${lang.Us}
            </h3>
            <div class="row">
                <div class="col-xl-7 ab-left">
                    <img src="images/ab.jpg" alt=" " class="img-fluid" />
                </div>
                <div class="col-xl-5 ab-right">
                    <h3 class="mb-4 pb-4">${lang.Welcome_to_Our_Transports}</h3>
                    <p>Thank you for taking the time to visit our site! sed do eiusmod tempor incididunt ut labore et
                        dolore magna aliqua.
                        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                        consequat.</p>
                    <p class="mt-3">Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
                        fugiat nulla pariatur.</p>
                </div>
            </div>
        </div>
    </div>
    <!-- //about -->

    <!-- middle slider -->
    <div class="w3agile-spldishes py-sm-5 py-4">
        <div class="container  py-xl-3">
            <h3 class="title text-white text-center mb-lg-5 mb-sm-4 mb-3">
                <span>V</span>ehicle
                <span>F</span>leets</h3>
            <div class="spldishes-grids">
                <div class="large-12 columns">
                    <!-- Owl-Carousel -->
                    <div id="owl-demo" class="owl-carousel owl-theme text-center agileinfo-gallery-row">
                        <a class="item g1">
                            <img class="lazyOwl img-fluid" src="images/m1.jpg" title="Transports" alt="" />
                        </a>
                        <a class="item g1">
                            <img class="lazyOwl img-fluid" src="images/m5.jpg" title="Transports" alt="" />
                        </a>
                        <a class="item g1">
                            <img class="lazyOwl img-fluid" src="images/m3.jpg" title="Transports" alt="" />
                        </a>
                        <a class="item g1">
                            <img class="lazyOwl img-fluid" src="images/m4.jpg" title="Transports" alt="" />
                        </a>
                        <a class="item g1">
                            <img class="lazyOwl img-fluid" src="images/m2.jpg" title="Transports" alt="" />
                        </a>
                        <a class="item g1">
                            <img class="lazyOwl img-fluid" src="images/m6.jpg" title="Transports" alt="" />
                        </a>
                    </div>
                    <a class="button secondary btn py-2 px-4 play">Play</a>
                    <a class="button secondary btn py-2 px-4 stop">Stop</a>
                </div>
            </div>
        </div>
    </div>
    <!-- //middle slider -->

    <!-- team -->
    <div class="w3ls-team py-sm-5 py-4">
        <div class="container py-xl-5 py-lg-3">
            <div class="row team-grids">
                <div class="col-lg-9">
                    <div class="row">
                        <div class="col-4 w3_agileits-team1">
                            <img class="img-fluid" src="images/t1.jpg" alt="">
                            <h5 class="mt-3">Elmore</h5>
                            <p>Lorem ipsum</p>
                            <div class="social-icons mt-2">
                                <ul>
                                    <li>
                                        <a href="#" class="fab fa-facebook-f icon-border facebook"> </a>
                                    </li>
                                    <li class="mx-1">
                                        <a href="#" class="fab fa-twitter  icon-border twitter"> </a>
                                    </li>
                                    <li>
                                        <a href="#" class="fab fa-google-plus-g icon-border googleplus"> </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-4 w3_agileits-team1">
                            <img class="img-fluid" src="images/t2.jpg" alt="">
                            <h5 class="mt-3">Blanton</h5>
                            <p>Lorem ipsum</p>
                            <div class="social-icons mt-2">
                                <ul>
                                    <li>
                                        <a href="#" class="fab fa-facebook-f icon-border facebook"> </a>
                                    </li>
                                    <li class="mx-1">
                                        <a href="#" class="fab fa-twitter  icon-border twitter"> </a>
                                    </li>
                                    <li>
                                        <a href="#" class="fab fa-google-plus-g icon-border googleplus"> </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-4 w3_agileits-team1">
                            <img class="img-fluid" src="images/t3.jpg" alt="">
                            <h5 class="mt-3"> Bass</h5>
                            <p>Lorem ipsum</p>
                            <div class="social-icons mt-2">
                                <ul>
                                    <li>
                                        <a href="#" class="fab fa-facebook-f icon-border facebook"> </a>
                                    </li>
                                    <li class="mx-1">
                                        <a href="#" class="fab fa-twitter  icon-border twitter"> </a>
                                    </li>
                                    <li>
                                        <a href="#" class="fab fa-google-plus-g icon-border googleplus"> </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4 w3_agileits-team1 mt-sm-5 mt-3">
                            <img class="img-fluid" src="images/t4.jpg" alt="">
                            <h5 class="mt-3">Halpin</h5>
                            <p>Lorem ipsum</p>
                            <div class="social-icons mt-2">
                                <ul>
                                    <li>
                                        <a href="#" class="fab fa-facebook-f icon-border facebook"> </a>
                                    </li>
                                    <li class="mx-1">
                                        <a href="#" class="fab fa-twitter  icon-border twitter"> </a>
                                    </li>
                                    <li>
                                        <a href="#" class="fab fa-google-plus-g icon-border googleplus"> </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-4 w3_agileits-team1 mt-sm-5 mt-3">
                            <img class="img-fluid" src="images/t5.jpg" alt="">
                            <h5 class="mt-3">Jesse </h5>
                            <p>Lorem ipsum</p>
                            <div class="social-icons mt-2">
                                <ul>
                                    <li>
                                        <a href="#" class="fab fa-facebook-f icon-border facebook"> </a>
                                    </li>
                                    <li class="mx-1">
                                        <a href="#" class="fab fa-twitter  icon-border twitter"> </a>
                                    </li>
                                    <li>
                                        <a href="#" class="fab fa-google-plus-g icon-border googleplus"> </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-4 w3_agileits-team1 mt-sm-5 mt-3">
                            <img class="img-fluid" src="images/t6.jpg" alt="">
                            <h5 class="mt-3">Jarvis</h5>
                            <p>Lorem ipsum</p>
                            <div class="social-icons mt-2">
                                <ul>
                                    <li>
                                        <a href="#" class="fab fa-facebook-f icon-border facebook"> </a>
                                    </li>
                                    <li class="mx-1">
                                        <a href="#" class="fab fa-twitter  icon-border twitter"> </a>
                                    </li>
                                    <li>
                                        <a href="#" class="fab fa-google-plus-g icon-border googleplus"> </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 team-right">
                    <h3 class="title mb-sm-4 mb-3">
                        <span>O</span>ur
                        <span>T</span>eam</h3>
                    <p>Great things in business are never done by one person. They're done by a team of people.</p>
                </div>
            </div>
        </div>
    </div>
    <!-- //team -->

    <!-- middle section -->
    <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>
    <!-- //middle section -->
    <!-- footer section -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer section -->
</body>

</html>