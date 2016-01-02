/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        //webView.loadDataWithBaseURL("about:blank", data, "text/html", "utf-8", null);
        webView.loadDataWithBaseURL("file:///android_asset/", "<link rel=\"stylesheet\" type=\"text/css\" href=\"dailycss.css\" />"+data, "text/html", "utf-8", null);
    }
}
