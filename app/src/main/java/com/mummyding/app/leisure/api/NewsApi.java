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
