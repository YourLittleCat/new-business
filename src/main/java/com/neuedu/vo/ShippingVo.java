package com.neuedu.vo;

import com.alipay.api.domain.ZhimaCreditEpInfoGetModel;
import com.neuedu.pojo.Shipping;

import java.io.Serializable;
import java.util.List;

public class ShippingVo implements Serializable{


    private Integer pageNum;
    private  Integer pageSize;
    private Integer size;
    private String orderBy;
    private Integer startRow;
    private Integer endRow;
    private Integer total;
    private Integer pages;
    private List<Shipping> list;
    private  Integer firstPage;
    private  Integer prePage;
    private Integer nextPage;
    private  Integer lastPage;
    private  boolean isFirstPage;
    private  boolean isLastPage;
    private  boolean hasPreviousPage;
    private  boolean hasNextPage;
    private  Integer navigatePages;
    private  Integer navigatepageNums;


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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
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

    public List<Shipping> getList() {
        return list;
    }

    public void setList(List<Shipping> list) {
        this.list = list;
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

    public ShippingVo(Integer pageNum, Integer pageSize, Integer size, String orderBy, Integer startRow, Integer endRow, Integer total, Integer pages, List<Shipping> list, Integer firstPage, Integer prePage, Integer nextPage, Integer lastPage, boolean isFirstPage, boolean isLastPage, boolean hasPreviousPage, boolean hasNextPage, Integer navigatePages, Integer navigatepageNums) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.size = size;
        this.orderBy = orderBy;
        this.startRow = startRow;
        this.endRow = endRow;
        this.total = total;
        this.pages = pages;
        this.list = list;
        this.firstPage = firstPage;
        this.prePage = prePage;
        this.nextPage = nextPage;
        this.lastPage = lastPage;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
        this.navigatePages = navigatePages;
        this.navigatepageNums = navigatepageNums;
    }

    public ShippingVo( ) {

    }
}
