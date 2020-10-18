<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html lang="en">
<c:set var="title" value="Admin Main page" />
<%@ include file="/WEB-INF/jspf/admin/head.jspf"%>

<body id="page-top">

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
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800 ml-5">Dashboard</h1>
                        <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i
                                class="fas fa-download fa-sm text-white-50"></i> Generate Report</a>
                    </div>

                    <!-- Content Row -->
                    <div class="row">

                        <!-- Dashboard -->
                        <c:forEach var="keyStatus" items="${dashboard.keySet()}">
                            <div class="card border-left-success shadow ml-5 mb-4" style="width: 22rem;">
                                <div class="card-header bg-warning font-weight-bold h3">
                                    ${keyStatus}
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:forEach var="rowValue" items="${dashboard.get(keyStatus)}">
                                        <li class="list-group-item">${rowValue} </li>
                                    </c:forEach>
                                </ul>
                                <a href="#" class="btn align-center ">Details</a>
                            </div>
                        </c:forEach>


                    </div>

                    <!-- Content Row -->




                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->
        </div>
        <!-- End of Content Wrapper -->

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

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/chart.js/Chart.min.js"></script>



</body>

</html>