package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2022-07-17 20:34
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity activity) {
        boolean flag = true;
        int count = activityDao.save(activity);
        if (1 != count) {
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        // 取total
        int total = activityDao.getTotalByCondition(map);
        // 取dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        // 将他们封装到vo中
        PaginationVo<Activity> vo = new PaginationVo<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        // 查询出需要删除备注的数量
        int count1 =  activityRemarkDao.getCountByAids(ids);
        // 删除备注返回收到影响的数量
        int count2 =  activityRemarkDao.getDeleteByAids(ids);
        // 删除市场活动
        int count3 = activityDao.delete(ids);
        if (count1 != count2){
            flag = false;
        }
        if (count3 != ids.length) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        // 取ulist
        List<User> uList = userDao.getUserList();
        // 取a
        Activity a = activityDao.getById(id);
        // 打包成map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uList", uList);
        map.put("a", a);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag = true;
        int count = activityDao.update(activity);
        if (1 != count) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> arList  = activityRemarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkDao.deleteById(id);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        int count = activityRemarkDao.saveRemark(activityRemark);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        int count = activityRemarkDao.updateRemark(activityRemark);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }
}
