package com.shilovich.hrbet.bean;

public class PageRequest {
    private Integer page;
    private Integer size;
    private SortEnum sortEnum;

    public PageRequest() {
    }

    public PageRequest(Integer page, Integer size, SortEnum sortEnum) {
        this.page = page;
        this.size = size;
        this.sortEnum = sortEnum;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public SortEnum getSortEnum() {
        return sortEnum;
    }

    public void setSortEnum(SortEnum sortEnum) {
        this.sortEnum = sortEnum;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("PageRequest{");
        builder.append("page=").append(page);
        builder.append(", size=").append(size);
        builder.append(", sortEnum=").append(sortEnum);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageRequest that = (PageRequest) o;

        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        return sortEnum != null ? sortEnum.equals(that.sortEnum) : that.sortEnum == null;
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (sortEnum != null ? sortEnum.hashCode() : 0);
        return result;
    }
}
