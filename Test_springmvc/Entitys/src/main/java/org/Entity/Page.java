package org.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xcyb
 * @date 2022/10/18 15:38
 */
public class Page<T> {

    private int pageSize;//每页显示多少天数据

    private int pageNo; //当前第几页

    private int pageCount; //总页数

    private int sum;//一共有多少条数据

    private List<T> list = new ArrayList<T>();

    private int sIndex;//数据库开始读取的下标

    public int getsIndex() {
        return sIndex;
    }

    public void setsIndex(int sIndex) {
        this.sIndex = sIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        //总数 %取模  每页显示多少条数据，
        if(sum % pageSize == 0){//如果正好为0 就没有多的数据形成
            return sum / pageSize;
        }else{                  //如果有多的数据，页数会多一页
            return (sum / pageSize) + 1;
        }
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", pageCount=" + pageCount +
                ", sum=" + sum +
                ", list=" + list +
                '}';
    }
}
