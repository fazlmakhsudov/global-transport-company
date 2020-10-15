package com.epam.gtc;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author Fazliddin Makhsudov
 */
public final class Path {

    public static final String PAGE_HOME = "/WEB-INF/jsps/common/index.jsp";
    public static final String PAGE_LOGIN = "/WEB-INF/jsps/common/login.jsp";
    public static final String PAGE_SIGNUP = "/WEB-INF/jsps/common/signup.jsp";
    public static final String PAGE_RATES = "/WEB-INF/jsps/common/rates.jsp";
    public static final String PAGE_GALLERY = "/WEB-INF/jsps/common/gallery.jsp";
    public static final String PAGE_ABOUT_US = "/WEB-INF/jsps/common/aboutUs.jsp";
    public static final String PAGE_CONTACT_US = "/WEB-INF/jsps/common/contactUs.jsp";
    public static final String PAGE_USER_CABINET = "/WEB-INF/jsps/user/userCabinet.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsps/common/errorPage.jsp";
    public static final String PAGE_404 = "/WEB-INF/jsps/common/404.jsp";
    public static final String PAGE_ADMIN_HOME = "/WEB-INF/jsps/admin/adminMainPage.jsp";
    public static final String PAGE_ADMIN_USERS = "/WEB-INF/jsps/admin/adminUsersPage.jsp";
    public static final String PAGE_ADMIN_CITIES = "/WEB-INF/jsps/admin/adminCitiesPage.jsp";
    public static final String PAGE_ADMIN_DELIVERIES = "/WEB-INF/jsps/admin/adminDeliveriesPage.jsp";
    public static final String PAGE_ADMIN_DISTANCES = "/WEB-INF/jsps/admin/adminDistancesPage.jsp";
    public static final String PAGE_ADMIN_INVOICES = "/WEB-INF/jsps/admin/adminInvoicesPage.jsp";
    public static final String PAGE_ADMIN_RATES = "/WEB-INF/jsps/admin/adminRatesPage.jsp";
    public static final String PAGE_ADMIN_REQUESTS = "/WEB-INF/jsps/admin/adminRequestsPage.jsp";

    public static final String AJAX_COMMAND_PERSONAL_COUNTER_FORM = "ajaxPersonalCounterForm";

    public static final String COMMAND_INDEX = "controller?command=index";
    public static final String COMMAND_USER_CABINET = "controller?command=userCabinet";
    public static final String COMMAND_USER_REQUESTS_TAB = "controller?command=userRequestsTab";
    public static final String COMMAND_USER_INVOICES_TAB = "controller?command=userInvoicesTab";
    public static final String COMMAND_USER_DELIVERIES_TAB = "controller?command=userDeliveriesTab";

    public static final String COMMAND_ADMIN_USERS_PAGE = "controller?command=adminUsersPage";
    public static final String COMMAND_ADMIN_CITIES_PAGE = "controller?command=adminCitiesPage";
    public static final String COMMAND_ADMIN_DELIVERIES_PAGE = "controller?command=adminDeliveriesPage";
    public static final String COMMAND_ADMIN_DISTANCES_PAGE = "controller?command=adminDistancesPage";
    public static final String COMMAND_ADMIN_INVOICES_PAGE = "controller?command=adminInvoicesPage";
    public static final String COMMAND_ADMIN_RATES_PAGE = "controller?command=adminRatesPage";
    public static final String COMMAND_ADMIN_REQUESTS_PAGE = "controller?command=adminRequestsPage";


    private Path() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}