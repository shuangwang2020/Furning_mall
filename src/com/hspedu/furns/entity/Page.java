package com.hspedu.furns.entity;

import java.util.List;

public class Page<T> {
    public static final Integer PAGE_SIZE = 3;

    private Integer pageNo; // 第几页

    private Integer pageSize = PAGE_SIZE; // 每页显示几条记录

    private Integer pageTotalCount; // 共有多少页 计算得到

    private Integer totalRow; // 共有多少条记录

    private List<T> items; // 当前页要显示的数据

    private String url; // 分页导航的字符串

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", pageTotalCount=" + pageTotalCount +
                ", totalRow=" + totalRow +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }
}
