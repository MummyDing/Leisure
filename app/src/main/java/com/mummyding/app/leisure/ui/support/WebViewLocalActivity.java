package com.mummyding.app.leisure.ui.support;


import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.Utils;
import com.mummyding.app.leisure.ui.support.BaseWebViewActivity;

public class WebViewLocalActivity extends BaseWebViewActivity {
    @Override
    protected String getData() {
        return  getIntent().getStringExtra(getString(R.string.id_html_content));
    }
    @Override
    protected void loadData() {
        webView.loadDataWithBaseURL("about:blank", data, "text/html", "utf-8", null);
    }
}
