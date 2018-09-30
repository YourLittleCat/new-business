package com.neuedu.vo;

import java.util.List;

public class OrderPageModel {
    private Integer pageNum;     //     "pageNum": 1,
    private Integer pageSize;        //             "pageSize": 3,
    private Integer size;        //             "size": 3,
    private boolean orderBy;           //             "orderBy": null,
    private Integer startRow;      //             "startRow": 1,
    private Integer endRow;         //             "endRow": 3,
    private Integer total;         //             "total": 16,
    private Integer pages;         //             "pages": 6,
    private List<OrderVo> orderVoList;
    private Integer firstPage;                   //    "firstPage": 1,
    private Integer prePage;                 //            "prePage": 0,
    private Integer nextPage;             //            "nextPage": 2,
    private Integer lastPage;               //            "lastPage": 6,
    private boolean isFirstPage;               //            "isFirstPage": true,
    private boolean isLastPage;             //            "isLastPage": false,
    private boolean hasPreviousPage;               //            "hasPreviousPage": false,
    private boolean hasNextPage;             //            "hasNextPage": true,
    private Integer navigatePages;               //            "navigatePages": 8,
    private Integer navigatepageNums;              //            "navigatepageNums"


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isOrderBy() {
        return orderBy;
    }

    public void setOrderBy(boolean orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<OrderVo> getOrderVoList() {
        return orderVoList;
    }

    public void setOrderVoList(List<OrderVo> orderVoList) {
        this.orderVoList = orderVoList;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public Integer getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(Integer navigatePages) {
        this.navigatePages = navigatePages;
    }

    public Integer getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(Integer navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
}
