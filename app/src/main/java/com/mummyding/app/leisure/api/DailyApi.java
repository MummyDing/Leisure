/*
 *  *  Copyright (C) 2015 MummyDing
 *  *
 *  *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  *  Leisure is free software: you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation, either version 3 of the License, or
 *  *  (at your option) any later version.
 *  *                             ｀
 *  *  Leisure is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *  *
 *  *  You should have received a copy of the GNU General Public License
 *  *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.api;

/**
 *  Created by mummyding on 15-11-22.<br>
 *  ZhiHu(link www.zhihu.com) Daily Api<br>
 *  @author MummyDing
 *  @version Leisure 1.0 1.1 / 2.0
 */
public class DailyApi {
    // Version 1.0 1.1
    //public static String daily_url = "https://diy-devz.rhcloud.com/zhihu";


    // Version 2.0
    public static String daily_url = "http://news-at.zhihu.com/api/4/news/latest";
    public static String daily_details_url="http://news-at.zhihu.com/api/4/news/";
    public static String daily_old_url= "http://news.at.zhihu.com/api/4/news/before/";
    public static String daily_story_base_url ="http://daily.zhihu.com/story/";

}
