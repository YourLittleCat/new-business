package com.neuedu.vo;

import java.io.Serializable;
import java.util.List;

public class PageModel<T> implements Serializable{



    //当前页的数据
    private List<T> data;

    //是否为第一页
    private boolean isFirst;

    //是否为最后一页

    private boolean isLast;

    //总共的页数
    private long totalPage;

    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "data=" + data +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                '}';
    }
}
