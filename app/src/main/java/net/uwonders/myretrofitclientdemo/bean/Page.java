package net.uwonders.myretrofitclientdemo.bean;

import java.util.List;

/**
 * Created by KangLong on 2017/9/26.
 */

public class Page<T> {
    private List<T> content;
    private  long total;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
