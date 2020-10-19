<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="${lang.Contact_Us}" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="${lang.Contact_Us}" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->

    <!-- contact -->
    <div class="contact py-sm-5 py-4">
        <div class="container py-xl-5 py-lg-3">
            <h3 class="title mb-sm-4 mb-3">
                ${lang.Contact}
                ${lang.Us}
              </h3>
            <div class="row">
                <div class="col-lg-7 contact-grid-agiles-w3l">
                    <div class="contact-grid-agile">
                        <!--<form action="#" method="post">
                            <div class="form-group">
                                <input class="form-control" type="text" placeholder="Name" name="name" required="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="email" placeholder="Email" name="email" required="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="text" placeholder="Subject" name="subject"
                                    required="">
                            </div>
                            <div class="form-group">
                                <textarea class="form-control" placeholder="Message.." name="message"
                                    required=""></textarea>
                            </div>
                            <input type="submit" class="btn" value="Send Now">
                        </form> -->
                    </div>
                    <div class="contact-right2 mt-4">
                        <div class="row call ">
                            <div class="col-4 contact-grdr-w3l">
                                <h2>Call us :</h2>
                            </div>
                            <div class="col-8 contact-grdr-w3l">
                                <ul>
                                    <li>+380 57 728 0628</li>
                                </ul>
                            </div>
                        </div>
                        <div class="row call my-4">
                            <div class="col-4 contact-grdr-w3l">
                                <h3>Locate us :</h3>
                            </div>
                            <div class="col-8 contact-grdr-w3l">
                                <ul>
                                    <li>23-ho Serpnya St, 33, Kharkiv, Kharkiv Oblast, 61000</li>
                                </ul>
                            </div>
                        </div>
                        <div class="row call">
                            <div class="col-4 contact-grdr-w3l">
                                <h3>Mail us :</h3>
                            </div>
                            <div class="col-8 contact-grdr-w3l">
                                <ul>
                                    <li>
                                        <a href="mailto:info@gtc.com">info@gtc.com</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-5 contact-right1 text-center mt-lg-0 mt-sm-5 mt-4">
                    <img src="images/contact.png" alt="" class="img-fluid" />
                </div>
            </div>
        </div>
    </div>
    <!-- //contact -->

    <!-- map -->
    <div class="mapf">
        <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d51358.99633074341!2d36.18662411735379!3d50.037383718739555!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4127a131b25962f9%3A0x51992032d0c2e7be!2sEpam%20Systems!5e1!3m2!1sen!2sua!4v1602268742530!5m2!1sen!2sua"></iframe>
    </div>
    <!--// map -->

    <!-- footer section -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer section -->

</body>

</html>