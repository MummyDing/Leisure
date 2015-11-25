package com.mummyding.app.leisure.model.science;

import java.io.Serializable;

/**
 * Created by mummyding on 15-11-17.
 */
public class ScienceBean implements Serializable {
    private ArticleBean[] result;


    public ArticleBean[] getResult() {
        return result;
    }

    public void setResult(ArticleBean[] result) {
        this.result = result;
    }
}
