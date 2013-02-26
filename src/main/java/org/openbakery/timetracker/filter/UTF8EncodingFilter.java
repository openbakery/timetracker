package org.openbakery.timetracker.filter;

import org.apache.wicket.util.crypt.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: rene
 * Date: 10.05.11
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class UTF8EncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(CharEncoding.UTF_8);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}
