package com.shilovich.hrbet.bean;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    private int pageNumber;
    private List<T> list=new ArrayList<>();

    public Page() {
    }

    public Page(int pageNumber, List<T> list) {
        this.pageNumber = pageNumber;
        this.list = list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
