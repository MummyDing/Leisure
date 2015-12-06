/*
 *
 *  * Copyright (C) 2015 MummyDing
 *  *
 *  * This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  * Leisure is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Leisure is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.api;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;

/**
 * Created by mummyding on 15-11-14.
 */
public class NewsApi {
    private static String [] newsUrl = null;
    private static String [] newsTitle = null;
    private static Document document = null;
    public static String [] getNewsUrl(){
        if(newsUrl == null){
            if(document == null) {
                InputStream is = Utils.readFileFromRaw(R.raw.news_api);
                document = Utils.getDocmentByIS(is);
            }
            NodeList urlList = document.getElementsByTagName("url");
            newsUrl = new String[urlList.getLength()];
            for(int i = 0 ; i < urlList.getLength();i++){
                newsUrl[i] = urlList.item(i).getTextContent();
            }
        }
        return newsUrl;
    }
    public static String [] getNewsTitle(){
        if(newsTitle == null){
            if(document == null) {
                InputStream is = Utils.readFileFromRaw(R.raw.news_api);
                 document = Utils.getDocmentByIS(is);
            }
            NodeList titleList = document.getElementsByTagName("name");
            newsTitle = new String[titleList.getLength()];
            for(int i = 0 ; i < titleList.getLength();i++){
                newsTitle[i] = titleList.item(i).getTextContent();
            }
        }
        return newsTitle;
    }
}
