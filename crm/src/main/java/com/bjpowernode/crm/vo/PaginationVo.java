package com.bjpowernode.crm.vo;

import java.util.List;

/**
 * @Author 临渊
 * @Date 2022-07-18 10:50
 */
public class PaginationVo<T> {
    private int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
