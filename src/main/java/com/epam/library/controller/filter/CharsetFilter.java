package com.epam.library.controller.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final String CHARACTER_ENCODING = "characterEncoding";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(CHARACTER_ENCODING);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
