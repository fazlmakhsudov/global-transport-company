/**
 *
 */
package com.epam.gtc.utils;

import javax.servlet.http.HttpServletRequest;

public final class Method {
    /**
     * Clarify whether it is Get request
     *
     * @param request
     *            request
     * @return boolean
     */
    public static boolean isGet(final HttpServletRequest request) {
        return request.getMethod().equals("GET");
    }

    /**
     * Clarify whether it is Post request
     *
     * @param request
     *            request
     * @return boolean
     */
    public static boolean isPost(final HttpServletRequest request) {
        return request.getMethod().equals("POST");
    }

    private Method() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}
