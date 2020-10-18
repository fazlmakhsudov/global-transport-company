<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Admin Deliveries page" />
<%@ include file="/WEB-INF/jspf/admin/head.jspf"%>

<body id="page-top">
    <c:set var="urlForPage" value="/gtc/controller?command=adminDeliveriesPage" scope="page" />
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
                    <p class="mb-4 text-danger font-weight-bold">${sessionScope.errorDelivery} </p>
                    <c:remove var="errorDelivery" />
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 row">
                            <h5 class="m-0 font-weight-bold text-primary ml-3 col">${lang.Deliveries}</h5>
                            <div class='col'>
                                <form class='form-inline justify-content-end' action="${urlForPage}" method='GET'>
                                    <button type="button" class="btn btn-outline-primary mr-5"
                                        id="addnewbutton">${lang.Add}</button>
                                    <input type='text' name='command' value='adminDeliveriesPage'
                                        style='display:none;' />
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
                                            <th>Delivery status</th>
                                            <th>Request id</th>
                                            <th>Created date</th>
                                            <th>Updated date</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr class='text-center align-middle'>
                                            <th>#</th>
                                            <th>ID</th>
                                            <th>Delivery status</th>
                                            <th>Request id</th>
                                            <th>Created date</th>
                                            <th>Updated date</th>
                                            <th>Actions</th>
                                        </tr>
                                    </tfoot>

                                    <tbody>
                                        <c:set var='order' value='1' />
                                        <c:forEach var="delivery" items="${adminDeliveries}">
                                            <tr>
                                                <td class='align-middle text-center delivery${delivery.id}'>
                                                    ${order}
                                                    <c:set var='order' value='${order + 1}' />
                                                </td>
                                                <td class='align-middle text-center delivery${delivery.id}'>
                                                    ${delivery.id}</td>
                                                <td class='align-middle text-center delivery${delivery.id}'>
                                                    ${delivery.deliveryStatusName}</td>
                                                <td class='align-middle text-center delivery${delivery.id}'>
                                                    ${delivery.requestId}</td>
                                                <td class='align-middle text-center delivery${delivery.id}'>
                                                    ${delivery.createdDate}</td>
                                                <td class='align-middle text-center delivery${delivery.id}'>
                                                    ${delivery.updatedDate}</td>
                                                <td>
                                                    <form action="${urlForPage}" method='POST'>
                                                        <input type='text' name='command' value='adminDeliveriesPage'
                                                            style='display:none;' />
                                                        <input type='text' name='page' value='${page}'
                                                            style='display:none;' />
                                                        <input type='text' name='itemsPerPage' value='${itemsPerPage}'
                                                            style='display:none;' />
                                                        <input type='text' name='deliveryid' value="${delivery.id}"
                                                            style='display:none;' />
                                                        <div class="input-group form-group">
                                                            <div class="input-group-prepend"
                                                                id='removeSubmit${delivery.id}' style='display:none'>
                                                                <button type="submit"
                                                                    class="btn btn-outline-danger">Do</button>
                                                            </div>
                                                            <select class="custom-select form-control" name='action'
                                                                data-deliveryid='${delivery.id}'
                                                                data-deliverystatusname='${delivery.deliveryStatusName}'>
                                                                <option selected>Choose...</option>
                                                                <option value="save">${lang.Save}</option>
                                                                <option value="remove">${lang.Remove}</option>
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
                            <mytags:pagination allItems='${deliveriesNumber}' itemsNumber='${adminDeliveries.size()}'
                                itemsPerPage='${itemsPerPage}' currentPage='${currentPage}' url='${urlForPage}'
                                itemName='deliveries' />
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
                        <input type='text' name='command' value='adminDeliveriesPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' id="deliveryid" name='deliveryid' value="${delivery.id}"
                            style='display:none;' />
                        <input type='text' name='action' value='save' style='display:none;' />
                        <div class="form-group">
                            <label for="deliverystatusname">Delivery status</label>
                            <select class="form-control" id='deliverystatusname' name='deliverystatusname'>
                                <option value='waiting_for_packaging'>waiting_for_packaging</option>
                                <option value='under_transportation'>under_transportation</option>
                                <option value='delivered'>delivered</option>
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
                    <h5 class="modal-title">Add new delivery</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action='${urlForPage}' method='POST'>
                    <div class="modal-body">
                        <input type='text' name='command' value='adminDeliveriesPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' name='action' value='add' style='display:none;' />
                        <div class="form-group">
                            <label for="deliverystatusname">Delivery status</label>
                            <select class="form-control" id='deliverystatusname' name='deliverystatusname'>
                                <option value='waiting_for_packaging'>waiting_for_packaging</option>
                                <option value='under_transportation'>under_transportation</option>
                                <option value='delivered'>delivered</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="deliveryrequestid">Request id</label>
                            <input type="number" class="form-control" id="deliveryrequestid" name='deliveryrequestid'
                                placeholder="Enter request id" min='1' />
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
            let deliveryid = $(this).data('deliveryid');
            let deliverystatusname = $(this).data('deliverystatusname');

            let rowClass = '.delivery' + deliveryid;
            let removeSubmit = '#removeSubmit' + deliveryid;
            $(removeSubmit).css('display', 'none');
            if ($(this).find(":selected").val() == 'save') {
                $(rowClass).css({ "color": "black" })
                $("#deliveryid").val(deliveryid);
                $("#deliverystatusname").val(deliverystatusname);
                $("#myModalUpdate").modal('show');
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
            $("#myModalAdd").modal('show');
        });
    </script>
</body>

</html>