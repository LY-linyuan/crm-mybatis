package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.CustomerDao;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2022-07-21 20:59
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<Customer> getCustomerName(String name) {
        List<Customer> customerList = customerDao.getCustomerName(name);
        return customerList;
    }
}
