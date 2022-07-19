package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.service.ClueService;

/**
 * @Author 临渊
 * @Date 2022-07-19 13:15
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}
