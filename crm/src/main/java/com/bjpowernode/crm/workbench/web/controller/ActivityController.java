package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.taglibs.standard.extra.spath.Step;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2022-07-17 10:07
 */
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String servletPath = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(servletPath)) {
            getUserList(request, response);
        } else if ("/workbench/activity/save.do".equals(servletPath)) {
            save(request, response);
        } else if("/workbench/activity/pageList.do".equals(servletPath)) {
            pageList(request, response);
        } else if ("/workbench/activity/delete.do".equals(servletPath)) {
            delete(request, response);
        } else if ("/workbench/activity/getUserListAndActivity.do".equals(servletPath)) {
            getUserListAndActivity(request, response);
        } else if ("/workbench/activity/update.do".equals(servletPath)) {
            update(request, response);
        } else if ("/workbench/activity/detail.do".equals(servletPath)) {
            detail(request, response);
        } else if ("/workbench/activity/getRemarkListByAid.do".equals(servletPath)){
            getRemarkListByAid(request, response);
        } else if ("/workbench/activity/deleteRemark.do".equals(servletPath)) {
            deleteRemark(request, response);
        } else if ("/workbench/activity/saveRemark.do".equals(servletPath)) {
            saveRemark(request, response);
        } else if ("/workbench/activity/updateRemark.do".equals(servletPath)) {
            updateRemark(request, response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行修改备注的操作");
        String id = request.getParameter("id");
        String nodeContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(nodeContent);
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag(editFlag);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.updateRemark(activityRemark);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(response, map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行添加备注的操作");
        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setActivityId(activityId);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag(editFlag);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.saveRemark(activityRemark);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        PrintJson.printJsonObj(response, map);

    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.deleteRemark(id);
        PrintJson.printJsonFlag(response, flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动的id取出备注信息列表");
        String activityId = request.getParameter("activityId");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);
        PrintJson.printJsonObj(response, arList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity activity = as.detail(id);
        request.setAttribute("a", activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动修改的操作");
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.update(activity);
        PrintJson.printJsonFlag(response ,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询信息列表根据市场活动id查询单条记录");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String, Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response, map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动删除操作");
        String[] ids = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response, flag);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        // 条件查询加分页查询
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String starDate = request.getParameter("starDate");
        String endDate = request.getParameter("endDate");

        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        // 计算出略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("starDate", starDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVo<Activity> vo = as.pageList(map);
        PrintJson.printJsonObj(response, vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动添加的操作");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        // 当前系统时间
        // 当前登录的用户
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.save(activity);
        PrintJson.printJsonFlag(response ,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList =  us.getUserList();
        PrintJson.printJsonObj(response, uList);
    }


}
