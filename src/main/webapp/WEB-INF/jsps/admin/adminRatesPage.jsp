<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Admin Rates page" />
<%@ include file="/WEB-INF/jspf/admin/head.jspf"%>

<body id="page-top">
    <c:set var="urlForPage" value="/gtc/controller?command=adminRatesPage" scope="page" />
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
                            <h5 class="m-0 font-weight-bold text-primary ml-3 col">${lang.Rates}</h5>
                            <div class='col'>
                                <form class='form-inline justify-content-end' action="${urlForPage}" method='GET'>
                                    <button type="button" class="btn btn-outline-primary mr-5"
                                        id="addnewbutton">${lang.Add}</button>
                                    <input type='text' name='command' value='adminRatesPage' style='display:none;' />
                                    <input type='text' name='page' value='${page}' style='display:none;' />
                                    <div class="form-group">
                                        <label class='mr-3' for="itemsperpage">${lang.Items_per_page}</label>
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
                                            <th>${lang.Name}</th>
                                            <th>Max ${lang.Weight}</th>
                                            <th>Max ${lang.Length}</th>
                                            <th>Max ${lang.Width}</th>
                                            <th>Max ${lang.Height}</th>
                                            <th>Max ${lang.Distance}</th>
                                            <th>${lang.Cost}</th>
                                            <th>${lang.Actions}</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr class='text-center align-middle'>
                                            <th>#</th>
                                            <th>ID</th>
                                            <th>${lang.Name}</th>
                                            <th>Max ${lang.Weight}</th>
                                            <th>Max ${lang.Length}</th>
                                            <th>Max ${lang.Width}</th>
                                            <th>Max ${lang.Height}</th>
                                            <th>Max ${lang.Distance}</th>
                                            <th>${lang.Cost}</th>
                                            <th>${lang.Actions}</th>
                                        </tr>
                                    </tfoot>

                                    <tbody>
                                        <c:set var='order' value='1' />
                                        <c:forEach var="rate" items="${adminRates}">
                                            <tr>
                                                <td class='align-middle text-center rate${rate.id}'>
                                                    ${order}
                                                    <c:set var='order' value='${order + 1}' />
                                                </td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.id}</td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.name}</td>

                                                <td class='align-middle text-center rate${rate.id}'>${rate.maxWeight}
                                                </td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.maxLength}
                                                </td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.maxWidth}
                                                </td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.maxHeight}
                                                </td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.maxDistance}
                                                </td>
                                                <td class='align-middle text-center rate${rate.id}'>${rate.cost}</td>
                                                <td>
                                                    <form action="${urlForPage}" method='POST'>
                                                        <input type='text' name='command' value='adminRatesPage'
                                                            style='display:none;' />
                                                        <input type='text' name='page' value='${page}'
                                                            style='display:none;' />
                                                        <input type='text' name='itemsPerPage' value='${itemsPerPage}'
                                                            style='display:none;' />
                                                        <input type='text' name='rateid' value="${rate.id}"
                                                            style='display:none;' />
                                                        <div class="input-group form-group">
                                                            <div class="input-group-prepend" id='removeSubmit${rate.id}'
                                                                style='display:none'>
                                                                <button type="submit"
                                                                    class="btn btn-outline-danger">Do</button>
                                                            </div>
                                                            <select class="custom-select form-control" name='action'
                                                                data-rateid='${rate.id}' data-ratename='${rate.name}'
                                                                data-ratemaxweight='${rate.maxWeight}'
                                                                data-ratemaxlength='${rate.maxLength}'
                                                                data-ratemaxwidth='${rate.maxWidth}'
                                                                data-ratemaxheight='${rate.maxHeight}'
                                                                data-ratemaxdistance='${rate.maxDistance}'
                                                                data-ratecost='${rate.cost}'>
                                                                <option selected>${lang.Choose}</option>
                                                                <option value="save"><i class="fa fa-pencil-square-o"
                                                                        aria-hidden="true"></i>${lang.Update}</option>
                                                                <option value="remove"><i class="fa fa-trash-o"
                                                                        aria-hidden="true"></i>${lang.Remove}</option>
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
                            <mytags:pagination allItems='${ratesNumber}' itemsNumber='${adminRates.size()}'
                                itemsPerPage='${itemsPerPage}' currentPage='${currentPage}' url='${urlForPage}'
                                itemName='rates' />
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
                        <input type='text' name='command' value='adminRatesPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' id="rateid" name='rateid' value="${rate.id}" style='display:none;' />
                        <input type='text' name='action' value='save' style='display:none;' />
                        <div class="form-group">
                            <label for="ratename">Name</label>
                            <input type="text" class="form-control" id="ratename" name='ratename'
                                placeholder="Enter name" minlength="4" />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxweight">Max Weight</label>
                            <input type="number" class="form-control" id="ratemaxweight" name='ratemaxweight'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxlength">Max Length</label>
                            <input type="number" class="form-control" id="ratemaxlength" name='ratemaxlength'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxwidth">Max Width</label>
                            <input type="number" class="form-control" id="ratemaxwidth" name='ratemaxwidth'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxheight">Max Height</label>
                            <input type="number" class="form-control" id="ratemaxheight" name='ratemaxheight'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxdistance">Max Distance</label>
                            <input type="number" class="form-control" id="ratemaxdistance" name='ratemaxdistance'
                                placeholder="Enter value" min='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratecost">Cost</label>
                            <input type="number" class="form-control" id="ratecost" name='ratecost'
                                placeholder="Enter value" min='1' />
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
                    <h5 class="modal-title">Add new rate</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action='${urlForPage}' method='POST'>
                    <div class="modal-body">
                        <input type='text' name='command' value='adminRatesPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' name='action' value='add' style='display:none;' />
                        <div class="form-group">
                            <label for="ratename">Name</label>
                            <input type="text" class="form-control" id="ratename" name='ratename'
                                placeholder="Enter name" minlength="4" defaultValue='' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxweight">Max Weight</label>
                            <input type="number" class="form-control" id="ratemaxweight" name='ratemaxweight'
                                placeholder="Enter value" min='1' defaultValue='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxlength">Max Length</label>
                            <input type="number" class="form-control" id="ratemaxlength" name='ratemaxlength'
                                placeholder="Enter value" min='1' defaultValue='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxwidth">Max Width</label>
                            <input type="number" class="form-control" id="ratemaxwidth" name='ratemaxwidth'
                                placeholder="Enter value" min='1' defaultValue='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxheight">Max Height</label>
                            <input type="number" class="form-control" id="ratemaxheight" name='ratemaxheight'
                                placeholder="Enter value" min='1' defaultValue='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratemaxdistance">Max Distance</label>
                            <input type="number" class="form-control" id="ratemaxdistance" name='ratemaxdistance'
                                placeholder="Enter value" min='1' defaultValue='1' />
                        </div>
                        <div class="form-group">
                            <label for="ratecost">Cost</label>
                            <input type="number" class="form-control" id="ratecost" name='ratecost'
                                placeholder="Enter value" min='1' defaultValue='1' />
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
            let rateid = $(this).data('rateid');
            let ratename = $(this).data('ratename');

            let ratemaxweight = $(this).data('ratemaxweight');
            let ratemaxlength = $(this).data('ratemaxlength');
            let ratemaxwidth = $(this).data('ratemaxwidth');
            let ratemaxheight = $(this).data('ratemaxheight');
            let ratemaxdistance = $(this).data('ratemaxdistance');
            let ratecost = $(this).data('ratecost');

            let rowClass = '.rate' + rateid;
            let removeSubmit = '#removeSubmit' + rateid;
            $(removeSubmit).css('display', 'none');
            if ($(this).find(":selected").val() == 'save') {
                $(rowClass).css({ "color": "black" })
                $("#rateid").val(rateid);
                $("#ratename").val(ratename);

                $("#ratemaxweight").val(ratemaxweight);
                $("#ratemaxlength").val(ratemaxlength);
                $("#ratemaxwidth").val(ratemaxwidth);
                $("#ratemaxheight").val(ratemaxheight);
                $("#ratemaxdistance").val(ratemaxdistance);
                $("#ratecost").val(ratecost);

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