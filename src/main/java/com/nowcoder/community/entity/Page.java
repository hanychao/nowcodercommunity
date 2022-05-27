package com.nowcoder.community.entity;

/**
 * @author hyc
 * @create 2022-05-21 17:53
 */
public class Page {

    private int current = 1;

    private int limit = 10;

    private int rows;//查询到数据的总条数

    private String path;//设置路径，用于分页时拼接请求链接复用

    public Page() {
    }

    public Page(int current, int limit, int rows, String path) {
        this.current = current;
        this.limit = limit;
        this.rows = rows;
        this.path = path;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows > 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //获取current页的起始行号

    /**
     *当userId为0时，数据库查出所有未被拉黑的帖子
     *，对于所有查出来的这些帖子，用分页的方式进行展示，
     * limit 起始行号，页面大小； 这里的起始行号其实是对于被查出来的这些数据而言
     * 通过每一页的起始行号和页面大小，可以将被查出来的所有帖子按页面大小数量的帖子作一页的显示到不同的页面
     * 比如当查出来10条数据，行号为0~9，分页时页面大小为5，那第一页显示数据行号为0~4，第二页显示数据行号为5~9.
     * 分页可以理解为把查出来的所有数据分区间展示。
     */
    public int getOffset() {
        return (current - 1) * limit;
    }

    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }
    public int getFrom(){
        int from = current-2;
        return from>=1?from:1;
    }
    public int getTo(){
        int to = current+2;
        int total = getTotal();
        return to<=total?to:total;
    }
}
