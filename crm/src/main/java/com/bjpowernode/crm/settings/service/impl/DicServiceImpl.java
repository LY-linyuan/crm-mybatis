package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 临渊
 * @Date 2022-07-19 13:27
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DicType> dtList = dicTypeDao.getTypeList();
        // 将字典类型遍历
        for (DicType dt: dtList) {
            String code = dt.getCode();
            // 根据每一个字典类型来取得字典类型的列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code + "List" , dvList);
        }
        return map;
    }
}
