package sky.domain;

import java.util.List;

/**
 * Created by Stelawliet on 17/11/15.
 */
public class PageBean {
    private List<User> data;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int totalPage;
    private int currentPage;
    private int totalCount;
    private int pageSize;

    public PageBean(List<User> data, int currentPage, int totalCount, int pageSize) {
        this.data = data;
        this.firstPage = 1;
        this.currentPage = currentPage;
        this.totalPage = totalCount % pageSize == 0
                ? totalCount / pageSize : totalCount / pageSize + 1;
        this.prePage = currentPage - 1 < firstPage ? currentPage : currentPage - 1;
        this.nextPage = currentPage + 1 > totalPage ? currentPage : currentPage + 1;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
    }

    public PageBean(int currentPage, int totalCount, int pageSize) {
        this.firstPage = 1;
        this.currentPage = currentPage;
        this.totalPage = totalCount % pageSize == 0
                ? totalCount / pageSize : totalCount / pageSize + 1;
        this.prePage = currentPage - 1 < firstPage ? currentPage : currentPage - 1;
        this.nextPage = currentPage + 1 > totalPage ? currentPage : currentPage + 1;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
    }



    public PageBean() {
    }


    public List<User> getData() {
        return data;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {

        return currentPage;
    }

    public int getTotalCount() {

        return totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStartPage() {
        return getCurrentPage() == 1 ? 0 : getPrePage() * getPageSize();
    }

    public int getLastPage() {
        int last = getStartPage() + getPageSize();

        if (last > data.size()) {
//            if (totalCount%pageSize==0){
//                last =getStartPage() + getPageSize();
//            }else {
            last = getStartPage() + (totalCount % pageSize);
//            }
        }
        return last;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "data=" + data +
                ", firstPage=" + firstPage +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                '}';
    }
}

