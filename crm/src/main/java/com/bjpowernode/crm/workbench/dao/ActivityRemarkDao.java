package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2022-07-17 21:48
 */
public interface ActivityRemarkDao {

    int getCountByAids(String[] ids);

    int getDeleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
