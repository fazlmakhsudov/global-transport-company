<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Gallery page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="Gallery" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->
    <!-- gallery -->
    <div class="gallery py-sm-5 py-4">
        <div class="container py-xl-5 py-lg-3">
            <h3 class="title mb-sm-4 mb-3">
                <span>O</span>ur
                <span>G</span>allery</h3>
            <div class="row gallery-grids">
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m1.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m1.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m8.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m8.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m3.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m3.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
            </div>
            <div class="row gallery-grids">
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m9.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m9.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m4.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m4.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m5.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m5.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
            </div>
            <div class="row gallery-grids">
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m6.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m6.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m7.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m7.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
                <div class="col-sm-4 gallery-grid wow fadeInUp animated" data-wow-delay=".5s">
                    <div class="grid">
                        <figure class="effect-apollo">
                            <a class="example-image-link" href="images/m2.jpg" data-lightbox="example-set"
                                data-title="">
                                <img src="images/m2.jpg" alt="" class="img-fluid" />
                            </a>
                            <figcaption>
                            </figcaption>
                        </figure>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- //gallery -->

    <!-- middle section -->
    <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>
    <!-- //middle section -->

    <!-- footer -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer -->

</body>

</html>