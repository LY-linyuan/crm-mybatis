package com.bjpowernode.crm.web.filter;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * @Author 临渊
 * @Date 2022-07-17 16:17
 */

public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 将请求放行
        chain.doFilter(request, response);
    }
}
