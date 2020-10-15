<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="Rates" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
   <!-- header -->
   	<header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="Rates" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->



    <!-- rate counter-->
    <c:if test = "${myRate != null}">
            <div class="container">
    			<h3 class="title mb-sm-4 text-center">
        				<span>R</span>ate
        				<span>C</span>ounter</h3>
    		</div>
    <div class='container col col-sm-5 col-md-3 col-lg-3 justify-content-center'>
              <form action='/gtc/controller'>
                                   <input type='text' name='command' value='rates' style='display:none;'/>
                                   <div class='form-group'>
                                        <p class='form-control text-center bg-info text-white'>Rate ${myRate.name}</p>
                                   </div>

                                   <div class='form-group'>
                                        <label for='ratecityfromid'>City from</label>
                                        <select class="form-control text-center" id='ratecityfromid'>
                                           <option value=''>Choose...</option>
                                           <c:forEach var="cityid" items="${distancesMap.keySet()}">
                                                <option id='cityoption${cityid}' value='${cityid}'
                                                data-citytoid='${distancesMap.get(cityid).toCityId}'
                                                data-citytoname='${citiesMap.get(distancesMap.get(cityid).toCityId)}'
                                                >${citiesMap.get(distancesMap.get(cityid).fromCityId)}</option>
                                           </c:forEach>
                                        </select>
                                   </div>
                                   <div class='form-group'>
                                        <label for='ratecitytoid'>City to</label>
                                        <input type="number" id='ratecitytoid' style='display:none;'>
                                        <p class="form-control text-center" id='showcitytoname'/>
                                 </div>

                                 <div class="form-group">
                                   <label for="rateweight">Weight</label>
                                   <input type="number" class="form-control ratefield text-center" id='rateweight'
                                     placeholder="Enter value" min='1' max='${myRate.maxWeight}'/>
                                 </div>
                                 <div class="form-group">
                                   <label for="ratelength">Length</label>
                                   <input type="number" class="form-control ratefield text-center" id='ratelength'
                                     placeholder="Enter value" min='1' max='${myRate.maxLength}'/>
                                 </div>
                                 <div class="form-group">
                                   <label for="ratewidth">Width</label>
                                   <input type="number" class="form-control ratefield text-center" id='ratewidth'
                                     placeholder="Enter value" min='1' max='${myRate.maxWidth}'/>
                                 </div>
                                 <div class="form-group">
                                   <label for="rateheight">Height</label>
                                   <input type="number" class="form-control ratefield text-center" id='rateheight'
                                     placeholder="Enter value" min='1' max='${myRate.maxHeight}'/>
                                 </div>

                                   <div class='form-group pl-3 pr-3 pb-1' id='showratecost' style='border: 2px teal solid;'>
                                        <label class='ml-2' for='ratecost'>Cost</label>
                                       <p class="form-control text-center" id='ratecost' name='ratecost'
                                          >${myRate.cost}</p>
                                   </div>
                                   <button class='btn btn-secondary text-center h3 w-100' type='submit'>Cancel</button>

              </form>
    </div>
    </c:if>
    <!-- // rate counter-->
	<!-- rates -->

    	<div class="py-sm-5 py-4" style="position:relative;">
    		<div class="container py-xl-5 py-lg-3">
    			<h3 class="title mb-sm-4 mb-3 text-center">
    				<span>A</span>ll
    				<span>R</span>ates</h3>
    			<div class="row">
    				 ${ctg:showrates(ratesList, ratesList.size())}
    			</div>
    		</div>
    	</div>

    <!-- //rates -->
    <!-- middle section -->
   <%@ include file="/WEB-INF/jspf/contact-us.jspf"%>
   <!-- //middle section -->

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<script>
    $('#ratecityfromid').on('select load click change', function() {

        let distanceid = $('#ratecityfromid').val();
        if (distanceid != '') {
            let cityoptionid = '#cityoption' + distanceid;
            let citytoid = $(cityoptionid).data('citytoid');
            let citytoname = $(cityoptionid).data('citytoname');
            $('#ratecitytoid').val(citytoid);
            $('#showcitytoname').text(citytoname);
        } else {
            $('#showcitytoname').text('');
        }
    });
    $('.ratefield').on('click change', function() {
        let max = $(this).attr('max');
        if ($(this).val() > max) {
            $(this).val(max);
        }
    });

</script>
</body>

</html>