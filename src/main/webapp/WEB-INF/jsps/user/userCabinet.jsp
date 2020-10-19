<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html lang="en">
<c:set var="title" value="Profile" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
    <!-- header -->
    <header>
        <%@ include file="/WEB-INF/jspf/navigation.jspf"%>
        <c:set var="navmenu" value="My Cabinet" />
        <%@ include file="/WEB-INF/jspf/sub-navigation.jspf"%>
    </header>
    <!-- header -->
    <c:set var="urlForPage" value="/gtc/controller" />
    <!-- user cabinet -->
    <div class='text-center'>
     <p class="mb-4 text-danger font-weight-bold">${sessionScope.errorRequests} </p>
      <c:remove var="errorRequests" />
      <p class="mb-4 text-danger font-weight-bold">${sessionScope.errorUser} </p>
            <c:remove var="errorUser" />
    <div>
    <div class="py-sm-5 py-4" style="position:relative;">
        <div class="ml-5 mr-5 py-xl-5 py-lg-3">
            <h3 class="title mb-sm-4 mb-3 text-center">
                ${lang.My}
                ${lang.Cabinet}
            </h3>
            <div class="row">
                <div class='col col-2 text-left'>
                    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                        <a class="nav-link ${profiletab}" id="v-pills-profile-tab" data-toggle="pill"
                            href="#v-pills-profile" role="tab" aria-controls="v-pills-profile" aria-selected="false">
                            <h5>${lang.Profile}</h5>
                        </a>
                        <a class="nav-link ${requeststab}" id="v-pills-requests-tab" data-toggle="pill"
                            href="#v-pills-requests" role="tab" aria-controls="v-pills-requests" aria-selected="false">
                            <h5>${lang.Requests}</h5>
                        </a>
                        <a class="nav-link ${invoicestab}" id="v-pills-invoices-tab" data-toggle="pill"
                            href="#v-pills-invoices" role="tab" aria-controls="v-pills-invoices" aria-selected="false">
                            <h5>${lang.Invoices}</h5>
                        </a>

                        <a class="nav-link ${deliveriestab}" id="v-pills-deliveries-tab" data-toggle="pill"
                            href="#v-pills-deliveries" role="tab" aria-controls="v-pills-deliveries"
                            aria-selected="false">
                            <h5>${lang.Deliveries}</h5>
                        </a>

                    </div>
                </div>
                <div class='col col-10'>
                    <div class="tab-content" id="v-pills-tabContent">
                        <div class="tab-pane fade ${profiletabbody}" id="v-pills-profile" role="tabpanel"
                            aria-labelledby="v-pills-profile-tab">
                            <%@ include file="/WEB-INF/jspf/user/profile.jspf"%>
                        </div>
                        <div class="tab-pane fade ${requeststabbody}" id="v-pills-requests" role="tabpanel"
                            aria-labelledby="v-pills-requests-tab">
                            <c:if test="${requestsNumber > 0}">
                                <%@ include file="/WEB-INF/jspf/user/requests.jspf"%>
                            </c:if>
                        </div>
                        <div class="tab-pane fade ${invoicestabbody}" id="v-pills-invoices" role="tabpanel"
                            aria-labelledby="v-pills-invoices-tab">
                            <c:if test="${invoicesNumber > 0}">
                                <%@ include file="/WEB-INF/jspf/user/invoices.jspf"%>
                            </c:if>
                        </div>
                        <div class="tab-pane fade ${deliveriestabbody}" id="v-pills-invoices" role="tabpanel"
                            aria-labelledby="v-pills-invoices-tab">
                            <c:if test="${deliveriesNumber > 0}">
                                <%@ include file="/WEB-INF/jspf/user/deliveries.jspf"%>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- //user cabinet -->

    <!-- footer section -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- /footer section -->
    <script>
        $('#editbutton').on('click', function () {
            $('#readprofile').css({ 'display': 'none' });
            $('#editprofile').css({ 'display': 'inherit' });

        });
        $('#cancelbutton').on('click', function () {
            $('#readprofile').css({ 'display': 'block' });
            $('#editprofile').css({ 'display': 'none' });

        });

        $('select.requests').on('change', function () {
            let requestid = $(this).data('requestid');
            let requestcontenttypename = $(this).data('requestcontenttypename');
            let requeststatusname = $(this).data('requeststatusname');

            let rowClass = '.request' + requestid;
            let removeSubmit = '#removeSubmit' + requestid;
            $(removeSubmit).css('display', 'none');
            if ($(this).find(":selected").val() == 'save') {
                $(rowClass).css({ "color": "black" })
                $("#requestid").val(requestid);
                $("#requestcontenttypename").val(requestcontenttypename);
                $("#requeststatusname").val(requeststatusname);

                $("#myModalUpdateRequest").modal('show');
            } else if ($(this).find(":selected").val() == 'remove') {

                $(removeSubmit).css('display', 'inherit');
                $(rowClass).css({ "color": "red" });
            } else {
                $(rowClass).css({ "color": "inherit" });
            }
        });
        $('#itemsperpage').on('click', function () {
            let previousValue = $(this).data('previous');
            let value = $(this).val();
            if (previousValue != value) {
                previousValue = value;
                $('#filtersubmit').css({ "display": "inherit" });
            } else {
                $('#filtersubmit').css({ "display": "none" });
            }
        });
        $('#addnewbutton').on('click', function () {
            $("#myModalPersonalCounter").modal('show');
        });
        // Request tab redirect
        $('#v-pills-requests-tab').on('click', function () {
            let url = '/gtc/controller?command=userRequestsTab';
            $(location).attr('href', url);
        });

        $('select.invoices').on('change', function () {
            let invoiceid = $(this).data('invoiceid');
            let invoicestatusname = $(this).data('invoicestatusname');

            let rowClass = '.invoice' + invoiceid;
            let removeSubmit = '#removeSubmit' + invoiceid;
            $(removeSubmit).css('display', 'none');
            if ($(this).find(":selected").val() == 'save') {
                $(rowClass).css({ "color": "black" })
                $("#invoiceid").val(invoiceid);

                $("#invoicestatusname").val(invoicestatusname);
                $("#myModalUpdateInvoice").modal('show');
            } else {
                $(rowClass).css({ "color": "inherit" });
            }
        });
        // Invoices tab redirect
        $('#v-pills-invoices-tab').on('click', function () {
            let url = '/gtc/controller?command=userInvoicesTab';
            $(location).attr('href', url);
        });

        // Deliveries tab redirect
        $('#v-pills-deliveries-tab').on('click', function () {
            let url = '/gtc/controller?command=userDeliveriesTab';
            $(location).attr('href', url);
        });

    </script>
</body>

</html>