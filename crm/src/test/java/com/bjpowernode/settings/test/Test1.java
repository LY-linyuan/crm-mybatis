package com.bjpowernode.settings.test;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 临渊
 * @Date 2022-07-17 10:48
 */
public class Test1 {
    public static void main(String[] args) {
        // 验证失效时间
        // 失效时间
        /*String expireTime = "2023-10-10 10:10:30";
        // 当前系统时间
        String currentTime = DateTimeUtil.getSysTime();
        int count = expireTime.compareTo(currentTime);
        System.out.println(count);*/

        /*String lockState = "0";
        if ("0".equals(lockState)) {
            System.out.println("账号已锁定");
        }*/

        // 验证IP地址
        /*// 浏览器的ip地址
        String ip = "192.168.1.3";
        // 允许访问的IP地址
        String allowIps = "192.168.1.1,192.168.1.2";
        if (allowIps.contains(ip)) {
            System.out.println("ip有效  可以访问");
        } else {
            System.out.println("ip受限  请联系管理员   不能访问");
        }*/


        String pwd = "123";
        String pwd1 = MD5Util.getMD5(pwd);
        System.out.println(pwd1);
    }
}
