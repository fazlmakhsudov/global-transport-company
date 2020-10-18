<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Main page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <%@ include file="/WEB-INF/jspf/service-carusel.jspf"%>
    </header>
    <!-- header -->
    <div class="container mt-5">
        <h3 class="title text-center mb-lg-5 mb-sm-4 mb-3">
            <span>W</span>e
            <span>S</span>uggest
            <span>R</span>ates</h3>
        <div class="card-deck mb-3 text-center">

            ${ctg:showrates(ratesList, 4)}

        </div>
    </div>

    <!-- middle slider -->
    <%@ include file="/WEB-INF/jspf/city-slider.jspf"%>

    <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>

    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>