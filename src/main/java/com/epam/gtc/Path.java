package com.epam.gtc;

/**
 * Path holder (jsp pages, controller commands).
 */
public final class Path {

    public static final String PAGE_HOME = "/WEB-INF/jsps/common/index.jsp";
    public static final String PAGE_LOGIN = "/WEB-INF/jsps/common/login.jsp";
    public static final String PAGE_SIGNUP = "/WEB-INF/jsps/common/signup.jsp";
    public static final String PAGE_RATES = "/WEB-INF/jsps/common/rates.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsps/common/error_page.jsp";
    public static final String PAGE_ADMIN_HOME = "/WEB-INF/jsps/admin/adminMainPage.jsp";
    public static final String PAGE_ADMIN_USERS = "/WEB-INF/jsps/admin/adminUsersPage.jsp";

    public static final String COMMAND_INDEX = "controller?command=index";
    public static final String COMMAND_ADMIN_USERS_PAGE = "controller?command=adminUsersPage";

    private Path() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}