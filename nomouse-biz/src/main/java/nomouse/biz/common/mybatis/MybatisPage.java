package nomouse.biz.common.mybatis;

import java.util.List;

public class MybatisPage<E> {

    private List<E> list;

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 起始行
     */
    private int offset;

    /**
     * 总数
     */
    private long total;

    /**
     * 是否分页
     */
    private boolean count;

    public MybatisPage(int pageNum, int pageSize, String orderBy, boolean count) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.count = count;
        this.offset = (this.pageNum - 1) * this.pageSize;
    }


    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public long getTotal() {
        return total;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public boolean isCount() {
        return count;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
