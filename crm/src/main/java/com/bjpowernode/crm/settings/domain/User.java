package com.bjpowernode.crm.settings.domain;

/**
 * @Author 临渊
 * @Date 2022-07-17 9:25
 */

/*
        日期  年月日       yyyy-MM-dd
        时间  年月日时分秒  yyyy-MM-dd HH:mm:ss
 */

/*
        关于登录 :
            验证账号密码
            验证其他信息
                expireTime  失效时间
                lockState   锁定状态
                allowIps    验证浏览器的IP地址是否有效
 */
public class User {             // 编号  主键
    private String id;
    private String loginAct;    // 登录账号
    private String name;        // 登录密码
    private String oginPwd;     // 用户的真实姓名
    private String email;       // 邮箱
    private String expireTime;  // 失效时间 19
    private String lockState;   // 锁定状态   0 锁定  1启用
    private String deptno;      // 部门编号
    private String allowIps;    // 允许访问的IP地址
    private String createTime;  // 创建时间
    private String createBy;    // 创建人
    private String editTime;    // 修改时间 19
    private String editBy;      // 修改人

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAct() {
        return loginAct;
    }

    public void setLoginAct(String loginAct) {
        this.loginAct = loginAct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOginPwd() {
        return oginPwd;
    }

    public void setOginPwd(String oginPwd) {
        this.oginPwd = oginPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public String getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(String allowIps) {
        this.allowIps = allowIps;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }
}
