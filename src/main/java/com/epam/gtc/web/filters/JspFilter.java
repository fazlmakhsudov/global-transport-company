package com.epam.gtc.web.filters;

import com.epam.gtc.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Index filter.
 *
 * @author Fazliddin Makhsudov
 */
public class JspFilter implements Filter {
    private static final Logger LOG =
            Logger.getLogger(JspFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) {
        LOG.debug("Filter initialization starts");
        LOG.debug("Filter initialization finished");
    }

    @Override
    public final void doFilter(final ServletRequest request,
                               final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("Filter starts");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if (path.contains("error.jsp") || path.contains("404.jsp")) {
            chain.doFilter(request, response);
            LOG.debug("Filter finished");
        } else {
            LOG.debug("Filter finished");
            ((HttpServletResponse) response).sendRedirect(
                    Path.COMMAND_INDEX);
        }

    }

    @Override
    public void destroy() {
        LOG.debug("Filter destruction starts");
        // no op
        LOG.debug("Filter destruction finished");
    }

}
