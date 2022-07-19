package com.bjpowernode.crm.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

/**
 * @Author 临渊
 * @Date 2022-07-17 17:57
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();
        if ("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath)) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");
            if (user!=null) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect(request.getContextPath()+ "/login.jsp");
            }
        }
    }
}
