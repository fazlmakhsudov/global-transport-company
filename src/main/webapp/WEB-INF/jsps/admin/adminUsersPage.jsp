<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Admin Users page" />
<%@ include file="/WEB-INF/jspf/admin/head.jspf"%>

<body id="page-top">
    <c:set var="urlForPage" value="/gtc/controller?command=adminUsersPage" scope="page" />
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
                            <h5 class="m-0 font-weight-bold text-primary ml-3 col">${lang.Users}</h5>
                            <div class='col'>
                                <form class='form-inline justify-content-end' action="${urlForPage}" method='GET'>
                                    <input type='text' name='command' value='adminUsersPage' style='display:none;' />
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
                                            <th>${lang.Name}</th>
                                            <th>${lang.Surname}</th>
                                            <th>${lang.Email}</th>
                                            <th>${lang.Role}</th>
                                            <th>Created date</th>
                                            <th>${lang.Actions}</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr class='text-center align-middle'>
                                            <th>#</th>
                                            <th>ID</th>
                                            <th>${lang.Name}</th>
                                            <th>${lang.Surname}</th>
                                            <th>${lang.Email}</th>
                                            <th>${lang.Role}</th>
                                            <th>Created date</th>
                                            <th>${lang.Actions}</th>
                                        </tr>
                                    </tfoot>

                                    <tbody>
                                        <c:set var='order' value='1' />
                                        <c:forEach var="user" items="${adminusers}">
                                            <tr>
                                                <td class='align-middle text-center user${user.id}'>
                                                    ${order}
                                                    <c:set var='order' value='${order + 1}' />
                                                </td>
                                                <td class='align-middle text-center user${user.id}'>${user.id}</td>
                                                <td class='align-middle text-center user${user.id}'>${user.name}</td>
                                                <td class='align-middle text-center user${user.id}'>${user.surname}</td>
                                                <td class='align-middle text-center user${user.id}'>${user.email}</td>
                                                <td class='align-middle text-center user${user.id}'>${user.roleName}
                                                </td>
                                                <td class='align-middle text-center user${user.id}'>${user.createdDate}
                                                </td>
                                                <td>
                                                    <form action="${urlForPage}" method='POST'>
                                                        <input type='text' name='command' value='adminUsersPage'
                                                            style='display:none;' />
                                                        <input type='text' name='page' value='${page}'
                                                            style='display:none;' />
                                                        <input type='text' name='itemsPerPage' value='${itemsPerPage}'
                                                            style='display:none;' />
                                                        <input type='text' name='userid' value="${user.id}"
                                                            style='display:none;' />
                                                        <div class="input-group form-group">
                                                            <div class="input-group-prepend" id='deleteSubmit${user.id}'
                                                                style='display:none'>
                                                                <button type="submit"
                                                                    class="btn btn-outline-danger">Do</button>
                                                            </div>
                                                            <select class="custom-select form-control" name='action'
                                                                data-userid='${user.id}' data-username='${user.name}'
                                                                data-usersurname='${user.surname}'
                                                                data-userrolename='${user.roleName}' }>
                                                                <option selected>${lang.Choose}</option>
                                                                <option value="update">${lang.Update}</option>

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
                            <mytags:pagination allItems='${usersNumber}' itemsNumber='${adminusers.size()}'
                                itemsPerPage='${itemsPerPage}' currentPage='${currentPage}' url='${urlForPage}'
                                itemName='users' />
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
    <div id="myModal" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Update</h5>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <form action='${urlForPage}' method='POST'>
                    <div class="modal-body">
                        <input type='text' name='command' value='adminUsersPage' style='display:none;' />
                        <input type='text' name='page' value='${page}' style='display:none;' />
                        <input type='text' name='itemsPerPage' value='${itemsPerPage}' style='display:none;' />
                        <input type='text' id="userid" name='userid' value="${user.id}" style='display:none;' />
                        <input type='text' name='action' value='update' style='display:none;' />
                        <div class="form-group">
                            <label for="username">Name</label>
                            <input type="text" class="form-control" id="username" name='username'
                                placeholder="Enter name" minlength="4" readonly />
                        </div>
                        <div class="form-group">
                            <label for="usersurname">Surname</label>
                            <input type="text" class="form-control" id="usersurname" name='usersurname'
                                placeholder="Enter surname" minlength="4" readonly>
                        </div>
                        <div class='form-group'>
                            <label for='userrolename'>Role</label>
                            <select class="form-control" id='userrolename' name='userrolename'>
                                <option value='admin'>admin</option>
                                <option value='manager'>manager</option>
                                <option value='user'>user</option>
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


    <!-- Custom script for update, delete-->
    <script src="js/sb-admin-2.min.js"></script>

    <script>
        $('select').on('change', function () {
            let userid = $(this).data('userid');
            let username = $(this).data('username');
            let usersurname = $(this).data('usersurname');
            let userrolename = $(this).data('userrolename');
            let rowClass = '.user' + userid;
            let deleteSubmit = '#deleteSubmit' + userid;
            $(deleteSubmit).css('display', 'none');
            if ($(this).find(":selected").val() == 'update') {
                $(rowClass).css({ "color": "black" })
                $("#userid").val(userid);
                $("#username").val(username);
                $("#usersurname").val(usersurname);
                $("#userrolename").val(userrolename);
                $("#myModal").modal('show');
            } else if ($(this).find(":selected").val() == 'delete') {

                $(deleteSubmit).css('display', 'inherit');
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
    </script>
</body>

</html>