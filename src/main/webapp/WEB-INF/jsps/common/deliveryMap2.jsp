<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="About Us" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<!-- header -->
       	<header>
            <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
            <c:set var="navmenu" value="About Us" />
            <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
        </header>
        <!-- header -->
     <!-- Delivery Map-->



        <div class='container'>
        <div class='col col-2'>
                                    <h2>City</h2>
                                <div>
                                <div class='col col-10'>
                                    <div>
                                        <h2>Available delivery cities</h2>
                                    </div>
                                    <div>

                                    </div>
                                <div>
            <br/><hr/>
             <c:forEach items="${deliveryMapDestinations.keySet()}" var="cityId">
                 <c:forEach items="${deliveryMapDestinations.get(cityId)}" var="distanceModel">
                    <h3>${distanceModel.fromCityId}</h3><br>
                    <h3>${distanceModel.toCityId}</h3><br>
                    <h3>${distanceModel.distance}</h3><br>
                 </c:forEach>
                 <br/><br/>
                <c:forEach items="${deliveryMapRates.get(cityId)}" var="rateModel">
                     <h3>${rateModel.name}</h3><br>
                     <h3>${rateModel.cost}</h3><br>
                  </c:forEach>
             </c:forEach>
        </div>
     <!-- Delivery Map-->

	<!-- middle section -->
       <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>
     <!-- //middle section -->
    <!-- footer section -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer section -->
</body>

</html>