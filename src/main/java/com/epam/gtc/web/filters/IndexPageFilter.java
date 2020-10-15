package com.epam.gtc.web.filters;

import com.epam.gtc.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Index filter.
 *
 * @author Fazliddin Makhsudov
 */
public class IndexPageFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public final void doFilter(final ServletRequest request,
                               final ServletResponse response, final FilterChain chain)
            throws IOException {
        ((HttpServletResponse) response).sendRedirect(
                Path.COMMAND_INDEX);
    }

    @Override
    public void destroy() {
    }

}
