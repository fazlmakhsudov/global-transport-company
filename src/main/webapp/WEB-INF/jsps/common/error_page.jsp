<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="main_bg">
		<div class="wrap">
			<div class="main">
				<div class="contact">

					<%-- CONTENT --%>

					<div class="well">The following error occurred</div>
					<c:set var="code"
						value="${requestScope['javax.servlet.error.status_code']}" />
					<c:set var="message"
						value="${requestScope['javax.servlet.error.message']}" />
					<c:set var="exception"
						value="${requestScope['javax.servlet.error.exception']}" />
					<c:if test="${not empty code}">
						<div class="alert alert-danger">Error code: ${code}</div>
					</c:if>
					<c:if test="${not empty message}">
						<div class="alert alert-danger">${message}</div>
					</c:if>
					<c:if test="${not empty exception}">
						<%
							exception.printStackTrace(new PrintWriter(out));
						%>
					</c:if>
					<%-- if we get this page using forward --%>
					<c:if test="${not empty requestScope.errorMessage}">
						<div class="alert alert-danger">${requestScope.errorMessage}</div>
					</c:if>
					<%-- CONTENT --%>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>