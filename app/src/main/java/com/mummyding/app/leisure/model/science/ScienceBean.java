package com.mummyding.app.leisure.model.science;

import java.io.Serializable;

/**
 * Created by mummyding on 15-11-17.
 */
public class ScienceBean implements Serializable {
    private String now;
    private boolean ok ;
    private int limit;
    private ArticleBean[] result;
    int offset;
    int total;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public ArticleBean[] getResult() {
        return result;
    }

    public void setResult(ArticleBean[] result) {
        this.result = result;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
