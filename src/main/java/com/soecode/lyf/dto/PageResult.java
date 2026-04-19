package com.soecode.lyf.dto;

import java.util.List;

/**
 * 分页结果封装
 */
public class PageResult<T> {

    private int pageNo;
    private int pageSize;
    private long total;
    private int totalPages;
    private List<T> list;

    public PageResult(int pageNo, int pageSize, long total, List<T> list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize > 0 ? pageSize : 1;
        this.total = total;
        this.list = list;
        this.totalPages = this.pageSize > 0 ? (int) Math.ceil((double) total / this.pageSize) : 0;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public boolean isHasPrev() {
        return pageNo > 1;
    }

    public boolean isHasNext() {
        return pageNo < totalPages;
    }
}
