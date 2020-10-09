<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="Rates" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
	<!-- rates -->
	    <!-- contact -->
    	<div class="py-sm-5 py-4" style="position:relative;">
    		<div class="container py-xl-5 py-lg-3">
    			<h3 class="title mb-sm-4 mb-3 text-center">
    				<span>A</span>ll
    				<span>R</span>ates</h3>
    			<div class="row">
    				 ${ctg:showrates(applicationScope['rates'], applicationScope['rates'].size())}
    			</div>
    		</div>
    	</div>
    	<!-- //contact -->

    <!-- //rates -->

<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>