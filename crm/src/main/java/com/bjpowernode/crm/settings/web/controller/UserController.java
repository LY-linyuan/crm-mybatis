package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2022-07-17 10:07
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String servletPath = request.getServletPath();
        if ("/settings/user/login.do".equals(servletPath)) {
             login(request, response);
        } else if ("/settings/user/xxx.do".equals(servletPath)) {
            // xxx(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        // 进入到login登录
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        // 密码名文密文
        loginPwd = MD5Util.getMD5(loginPwd);
        // 获取浏览器端的ip地址
        String ip = request.getRemoteAddr();
        System.out.println("ip ==========" + ip);
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = us.login(loginAct, loginPwd, ip);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            PrintJson.printJsonFlag(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            PrintJson.printJsonObj(response, map);
        }

    }


}
