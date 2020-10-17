<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="Rates" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
   <!-- header -->
   	<header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="Delivery Map" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->




	<!-- rates -->

    	<div class="py-sm-5 py-4" style="position:relative;">
    		<div class="container py-xl-5 py-lg-3">
    			<h3 class="title mb-sm-4 mb-3 text-center">
    				<span>D</span>elivery
    				<span>M</span>ap</h3>
    			<div class="row">
                    <div class='col-4 col-sm-4 col-md-3 col-lg-3'>
                        <h3 class='text-center'>City</h3>
                        <input type='text' class='text-center' id='searchcity' placeholder='Type city name' minlength='3'/>
                    </div>
                    <div class='col-8 col-sm-8 col-md-9 col-lg-9'>
                        <div class='row'>
                            <div class='col-12 col-sm-6 col-md-6'>
                                <h4 class='text-center'>Available delivery cities</h4>
                            </div>
                            <div class='col-12 col-sm-6 col-md-6'>
                                <h4 class='text-center'>Available rates</h4>
                            </div>
                        </div>
                     </div>
    			</div>
    			<hr class='bg-secondary' style='height:5px;'>
    			<div id='deliverymap'>
                 <c:forEach items="${deliveryMapDestinations.keySet()}" var="cityId">
                    <div class="row" id='city${citiesMap.get(cityId)}'>
                        <div class='col-4 col-sm-4 col-md-3 col-lg-3 align-middle'>
                            <h5 class='text-center city'>${citiesMap.get(cityId)}</h5>
                        </div>

                        <div class='col-8 col-sm-8 col-md-9 col-lg-9'>

                            <div class='row'>
                              <div class='col-12 col-sm-6 col-md-6'>
                                    <c:forEach items="${deliveryMapDestinations.get(cityId)}" var="distanceModel">
                                      <p class='text-center'>${citiesMap.get(distanceModel.toCityId)} /
                                        ${distanceModel.distance} km
                                      </p>
                                   </c:forEach>
                              </div>
                              <div class='col-12 col-sm-6 col-md-6' >
                                 <c:forEach items="${deliveryMapRates.get(cityId)}" var="rateModel">
                                    <p class='text-center'><a href='http://localhost:8080/gtc/controller?command=rates&ratename=${rateModel.name}'>
                                        ${rateModel.name} / $ ${rateModel.cost}
                                       </a>
                                    </p>
                                 </c:forEach>
                              </div>
                            </div>
                        </div>
                    </div>
                    <br/>
                </c:forEach>
               </div>
               <div id='searchresult'>
               </div>
    		</div>
    	</div>

    <!-- //rates -->
    <!-- middle section -->
   <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>
   <!-- //middle section -->

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script>
    $('#searchcity').on('click change', function() {
        $('#searchresult').html('');
        let searchtext = $(this).val();
        if (searchtext.length > 3) {
            $('#deliverymap').css({'display':'none'});
            let cities = $('.city');
            let html = '';
            for (let i = 0; i < cities.length; i++) {
                let cityname = $(cities[i]).text();

                    if (cityname.indexOf(searchtext) !== -1) {
                        let id = '#city' + cityname;

                        html = html + "<div class='row'>" + $(id).html() + "</div>";
                    }
            }
            $('#searchresult').html(html);
        } else {
            $('#deliverymap').css({'display': "inherit"});
        }
    });

</script>
</body>

</html>