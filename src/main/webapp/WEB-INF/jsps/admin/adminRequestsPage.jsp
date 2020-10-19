<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Admin Requests page" />
<%@ include file="/WEB-INF/jspf/admin/head.jspf"%>

<body id="page-top">
    <c:set var="urlForPage" value="/gtc/controller?command=adminRequestsPage" scope="page" />
    <!-- Page Wrapper -->
    <div id="wrapper">

        <%@ include file="/WEB-INF/jspf/admin/sidebar.jspf"%>

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <%@ include file="/WEB-INF/jspf/admin/topbar.jspf"%>
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">${lang.Tables}</h1>
                    <p class="mb-4">${lang.admin_tables_info}</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 row">
                            <h5 class="m-0 font-weight-bold text-primary ml-3">${lang.Requests}</h5>
                            <div class='col'>
                                <form class='form-inline justify-content-end' action="${urlForPage}" method='GET'>


                                    <div class='form-group ml-5 mr-2'>
                                        <select class="form-control" id='sortparameter' name='sortparameter'
                                            value='${sortparameter}'>
                                            <option value=''>${lang.Choose}</option>
                                            <option value='requestid'>Id</option>
                                            <option value='requestcityfromid'>City from</option>
                                            <option value='requestcitytoid'>City to</option>
                                            <option value='requestdeliverydate'>Delivery date</option>
                                        </select>
                                    </div>
                                    <button type="submit"
                                        class="form-control btn btn-outline-primary mr-5">Sort</button>


                                    <input type='text' name='command' value='adminRequestsPage' style='display:none;' />
                                    <input type='text' name='page' value='${page}' style='display:none;' />
                                    <div class="form-group">
                                        <label class='mr-3' for="itemsperpage">Items per page</label>
                                        <input type="number" class="form-control text-center" id="itemsperpage"
                                            value='${itemsPerPage}' name='itemsPerPage' min='2' max='20'
                                            data-previous='${itemsPerPage}' />
                                    </div>
                                    <div class="form-group" id='filtersubmit' style='display:none;'>
                                        <button type="submit" class="btn btn-outline-primary ml-3">${lang.Save}</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr class='text-center align-middle'>
                                            <th>#</th>
                                            <th>ID</th>
                                            <th>${lang.City_from}</th>
                                            <th>${lang.City_to}</th>
                                            <th>${lang.Weight}</th>
                                            <th>${lang.Length}</th>
                                            <th>${lang.Width}</th>
                                            <th>${lang.Height}</th>
                                            <th>${lang.Delivery_date}</th>
                                            <th>User id</th>
                                            <th>${lang.Content_type}</th>
                                            <th>${lang.Request_status}</th>
                                            <th>${lang.Created_date}</th>
                                            <th>${lang.Updated_date}</th>
                                            <th>${lang.Actions}</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr class='text-center align-middle'>
                                            <th>#</th>
                                            <th>ID</th>
                                            <th>${lang.City_from}</th>
                                            <th>${lang.City_to}</th>
                                            <th>${lang.Weight}</th>
                                            <th>${lang.Length}</th>
                                            <th>${lang.Width}</th>
                                            <th>${lang.Height}</th>
                                            <th>${lang.Delivery_date}</th>
                                            <th>User id</th>
                                            <th>${lang.Content_type}</th>
                                            <th>${lang.Request_status}</th>
                                            <th>${lang.Created_date}</th>
                                            <th>${lang.Updated_date}</th>
                                            <th>${lang.Actions}</th>
                                        </tr>
                                    </tfoot>

                                    <tbody>
                                        <c:set var='order' value='1' />
                                        <c:forEach var="request" items="${adminRequests}">
                                            <tr>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${order}
                                                    <c:set var='order' value='${order + 1}' />
                                                </td>
                                                <td class='align-middle text-center request${request.id}'>${request.id}
                                                </td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${citiesMap.get(request.cityFromId)}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${citiesMap.get(request.cityToId)}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.weight}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.length}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.width}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.height}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.deliveryDate}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.userId}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.contentTypeName}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.requestStatusName}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.createdDate}</td>
                                                <td class='align-middle text-center request${request.id}'>
                                                    ${request.updatedDate}</td>

                                                <td>
                                                    <form action="${urlForPage}" method='POST'>
                                                        <input type='text' name='command' value='adminRequestsPage'
                                                            style='display:none;' />
                                                        <input type='text' name='page' value='${page}'
                                                            style='display:none;' />
                                                        <input type='text' name='itemsPerPage' value='${itemsPerPage}'
                                                            style='display:none;' />
                                                        <input type='text' name='requestid' value="${request.id}"
                                                            style='display:none;' />
                                                        <div class="input-group form-group">
                                                            <div class="input-group-prepend"
                                                                id='removeSubmit${request.id}' style='display:none'>
                                                                <button type="submit"
                                                                    class="btn btn-outline-danger">Do</button>
                                                            </div>
                                                            <select class="custom-select form-control" name='action'
                                                                data-requestid='${request.id}'
                                                                data-requestdeliverydate='${request.deliveryDate}'
                                                                data-requestStatusName='${request.requestStatusName}'>
                                                                <option selected>${lang.Choose}</option>
                                                                <option value="save"><i class="fa fa-pencil-square-o"
                                                                        aria-hidden="true"></i>${lang.Update}</option>
                                                                <option value="createinvoice"><i
                                                                        class="fa fa-pencil-square-o"
                                                                        aria-hidden="true"></i>Create invoice</option>

                                                            </select>
                                                        </div>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- Pagination -->
                            <mytags:pagination allItems='${requestsNumber}' itemsNumber='${adminRequests.size()}'
                                itemsPerPage='${itemsPerPage}' currentPage='${currentPage}' url='${urlForPage}'
                                itemName='requests' />
                            <!--End of Pagination -->
                        </div>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

        </div>
        <!-- End of Content Wrapper -->
    </div>
    </div>
    <!-- End of Page Wrapper -->
    <!-- Footer -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- End of Footer -->
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <%@ include file="/WEB-INF/jspf/admin/logoutmodal.jspf"%>

    <!-- Modal HTML -->
    <div id="myModalUpdate" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Save changes</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action='${urlForPage}' method='POST'>
                    <div class="modal-body">
                        <input type='text' name='command' value='adminRequestsPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' id="requestid" name='requestid' value="${request.id}"
                            style='display:none;' />
                        <input type='text' name='action' value='save' style='display:none;' />

                        <div class='form-group'>
                            <label for='requeststatusname'>Request status</label>
                            <select class="form-control" id='requeststatusname' name='requeststatusname'>
                                <option value='waiting_for_manager_review' style='display:none;'>waiting_for_manager_review</option>
                                <option value='waiting_for_payment'>waiting_for_payment</option>
                                <option value='processed'>processed</option>

                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal HTML -->
    <div id="myModalAdd" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add new request</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action='${urlForPage}' method='POST'>
                    <div class="modal-body">
                        <input type='text' name='command' value='adminRequestsPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' name='action' value='add' style='display:none;' />

                        <div class='form-group'>
                            <label for='requestcityfromid'>City from</label>
                            <select class="form-control" id='requestcityfromid' name='requestcityfromid'>
                                <option value=''>Choose...</option>
                                <c:forEach var="cityid" items="${citiesMap.keySet()}">
                                    <option value='${cityid}'>${citiesMap.get(cityid)}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class='form-group'>
                            <label for='requestcitytoid'>City from</label>
                            <select class="form-control" id='requestcitytoid' name='requestcitytoid'>
                                <option value=''>Choose...</option>
                                <c:forEach var="cityid" items="${citiesMap.keySet()}">
                                    <option value='${cityid}'>${citiesMap.get(cityid)}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="requestweight">Max Weight</label>
                            <input type="number" class="form-control" id="requestweight" name='requestweight'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="requestlength">Max Length</label>
                            <input type="number" class="form-control" id="requestlength" name='requestlength'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="requestwidth">Max Width</label>
                            <input type="number" class="form-control" id="requestwidth" name='requestwidth'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="requestheight">Max Height</label>
                            <input type="number" class="form-control" id="requestheight" name='requestheight'
                                placeholder="Enter value" min='1' />
                        </div>

                        <div class="form-group">
                            <label for="requestdeliverydate">Delivery date</label>
                            <input type="datetime-local" class="form-control" id="requestdeliverydate"
                                name='requestdeliverydate' placeholder="Enter date" />
                        </div>
                        <div class="form-group">
                            <label for="requestuserid">User id</label>
                            <input type="number" class="form-control" id="requestuserid" name='requestuserid'
                                placeholder="Enter value" min='1' />
                        </div>


                        <div class='form-group'>
                            <label for='requestcontenttypename'>Content type</label>
                            <select class="form-control" id='requestcontenttypename' name='requestcontenttypename'>
                                <option value='cargo'>cargo</option>
                                <option value='parcel_post'>parcel_post</option>
                                <option value='document'>document</option>
                                <option value='jewelery'>jewelery</option>
                            </select>
                        </div>

                        <div class='form-group'>
                            <label for='requeststatusname'>Request status</label>
                            <select class="form-control" id='requeststatusname' name='requeststatusname'>
                                <option value='waiting_for_manager_review'>waiting_for_manager_review</option>
                                <option value='waiting_for_payment'>waiting_for_payment</option>
                                <option value='processed'>processed</option>
                                <option value='cancelled'>cancelled</option>
                            </select>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal HTML -->
    <div id="myModalCreateInvoice" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add new invoice</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action='/gtc/controller?command=adminInvoicesPage' method='POST'>
                    <div class="modal-body">
                        <input type='text' name='command' value='adminInvoicesPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' name='action' value='add' style='display:none;' />
                        <div class="form-group">
                            <label for="invoicecost">Cost</label>
                            <input type="number" class="form-control" id="invoicecost" name='invoicecost'
                                placeholder="Enter cost" min="1" />
                        </div>
                        <div class="form-group">
                            <label for="invoicestatusname">Invoice status</label>
                            <select class="form-control" id='invoicestatusname' name='invoicestatusname'>
                                <option value='unpaid'>Unpaid</option>

                            </select>
                        </div>
                        <div class="form-group">
                            <label for="invoicerequestid">Request id</label>
                            <input type="number" class="form-control" id="invoicerequestid" name='invoicerequestid'
                                placeholder="Enter request id" min='1' readonly />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!-- Custom script for save, remove-->
    <script src="js/sb-admin-2.min.js"></script>

    <script>
        $('select').on('change', function () {
            let requestid = $(this).data('requestid');
            let requestdeliverydate = $(this).data('requestdeliverydate');
            let requeststatusname = $(this).data('requeststatusname');

            let rowClass = '.request' + requestid;
            let removeSubmit = '#removeSubmit' + requestid;
            $(removeSubmit).css('display', 'none');
            if ($(this).find(":selected").val() == 'save'
                && requeststatusname != 'cancelled') {
                $(rowClass).css({ "color": "black" })
                $("#requestid").val(requestid);
                $("#requestdeliverydate").val(requestdeliverydate);
                $("#requeststatusname").val(requeststatusname);
                $("#myModalUpdate").modal('show');
            } else if ($(this).find(":selected").val() == 'remove') {

                $(removeSubmit).css('display', 'inherit');
                $(rowClass).css({ "color": "red" });
            } else if ($(this).find(":selected").val() == 'createinvoice'
                 && requeststatusname != 'cancelled') {

                $("#invoicerequestid").val(requestid);

                $("#myModalCreateInvoice").modal('show');
            }
            else {
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
            $("#myModalAdd").modal('show');
        });
    </script>
</body>

</html>