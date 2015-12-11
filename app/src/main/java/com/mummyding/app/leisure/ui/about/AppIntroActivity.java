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

package com.mummyding.app.leisure.ui.about;

import com.mummyding.app.leisure.ui.support.WebViewUrlActivity;

/**
 * Created by mummyding on 15-12-11.
 * GitHub: https://github.com/MummyDing/
 * Blog: http://blog.csdn.net/mummyding
 */
public class AppIntroActivity extends WebViewUrlActivity {
    @Override
    protected void loadData() {
        webView.loadUrl("file:///android_asset/LeisureIntroduction.html");
    }
}
