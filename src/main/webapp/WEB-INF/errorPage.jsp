<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="Error" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->

    <div class="container">
        <div class="col-6 justify-content-center h-50">
            <div class="bg-info h3 font-weight-bold text-center mt-5">The following error occurred</div>
            <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}" />
            <c:set var="message" value="${requestScope['javax.servlet.error.message']}" />
            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}" />
            <c:if test="${not empty code}">
                <div class="alert alert-danger">Error code: ${code}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert alert-danger">${message}</div>
            </c:if>
            <c:if test="${not empty exception}">
                ${exception.message}
            </c:if>
            <%-- if we get this page using forward --%>
            <c:if test="${not empty requestScope.errorMessage}">
                <div class="alert alert-danger">${requestScope.errorMessage}</div>
            </c:if>
        </div>
    </div>
    <!-- footer section -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer section -->

</body>

</html>