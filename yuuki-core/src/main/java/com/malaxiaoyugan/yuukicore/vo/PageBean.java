package com.malaxiaoyugan.yuukicore.vo;

import java.util.ArrayList;
import java.util.List;


public class PageBean<T> {
    private long total;// 总记录数
    private long pageNumber;// 需要显示的页码
    private long pageSize;// 每页记录数
    private long totalPages;   // 总页数
    private boolean isHavePrePage = false;  // 是否有上一页
    private boolean isHaveNextPage = false; // 是否有下一页

    private List<T> pageDatas = new ArrayList<T>();

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHavePrePage() {
        return isHavePrePage;
    }

    public void setHavePrePage(boolean havePrePage) {
        isHavePrePage = havePrePage;
    }

    public boolean isHaveNextPage() {
        return isHaveNextPage;
    }

    public void setHaveNextPage(boolean haveNextPage) {
        isHaveNextPage = haveNextPage;
    }

    public List<T> getPageDatas() {
        return pageDatas;
    }

    public void setPageDatas(List<T> pageDatas) {
        this.pageDatas = pageDatas;
    }
}
