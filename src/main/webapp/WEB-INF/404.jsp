<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="${lang.Page_not_found}" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="${lang.Page_not_found}" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->

    <!-- 404 -->
    <div class="services py-sm-5 py-4">
        <div class="container py-xl-5 py-lg-3">
            <h3 class="title mb-sm-4 mb-3">
                ${lang.Error}
                ${lang.Page}
            </h3>
            <div class="agile-info text-center">
                <h4>404</h4>
                <h3>${lang.SORRY}</h3>
                <p class="my-sm-4 mt-3">${lang.The_Page_Youre_Looking_for_Was_Not_Found}</p>
            </div>
        </div>
    </div>
    <!-- //404 -->

    <!-- middle section -->
    <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>
    <!-- //middle section -->
    <!-- footer section -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer section -->
</body>

</html>