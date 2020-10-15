package com.epam.gtc.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Encoding filter.
 *
 * @author Fazliddin Makhsudov
 */
public class EncodingFilter implements Filter {
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
    /**
     * Encoding.
     */
    private String encoding;

    @Override
    public final void destroy() {
        LOG.debug("Filter destruction starts");
        // no op
        LOG.debug("Filter destruction finished");
    }

    @Override
    public final void doFilter(final ServletRequest request,
                               final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        LOG.debug("Filter starts");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        LOG.trace("Request uri --> " + httpRequest.getRequestURI());

        String requestEncoding = request.getCharacterEncoding();
        if (requestEncoding == null) {
            LOG.trace("Request encoding = null, set encoding --> " + encoding);
            request.setCharacterEncoding(encoding);
        }

        LOG.debug("Filter finished");
        chain.doFilter(request, response);
    }

    @Override
    public final void init(final FilterConfig fConfig)
            throws ServletException {
        LOG.debug("Filter initialization starts");
        encoding = fConfig.getInitParameter("encoding");
        LOG.trace("Encoding from web.xml --> " + encoding);
        LOG.debug("Filter initialization finished");
    }

}