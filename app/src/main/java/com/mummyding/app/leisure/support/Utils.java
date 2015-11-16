package com.mummyding.app.leisure.support;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mummyding on 15-11-13.
 */
public class Utils {
    private static Context mContext = LeisureApplication.AppContext;
    public static InputStream readFileFromRaw(int fileID){
       return mContext.getResources()
               .openRawResource(fileID);
    }
    public static Document getDocmentByIS(InputStream is){
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc =null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
             doc = builder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc ;
    }
    public static String RegexFind(String regex,String string){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String res = string;
        while (matcher.find()){
            res = matcher.group();
        }
        return res.substring(1,res.length() - 1);
    }
    public static String RegexReplace(String regex,String string,String replace){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.replaceAll(replace);
    }
    public static boolean hasString(String str){
        if(str == null || str.equals("")) return false;
        return true;
    }
    public static void showToast(String text){
        Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
    }
    public static void DLog(String text){
        Log.d("调试数据：",text);
    }
}
