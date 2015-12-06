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

package com.mummyding.app.leisure.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.database.DatabaseHelper;
import com.mummyding.app.leisure.database.table.DailyTable;
import com.mummyding.app.leisure.database.table.NewsTable;
import com.mummyding.app.leisure.database.table.ReadingTable;
import com.mummyding.app.leisure.database.table.ScienceTable;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;
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
             is.close();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc ;
    }
    public static String RegexFind(String regex,String string,int start,int end){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String res = string;
        while (matcher.find()){
            res = matcher.group();
        }
        return res.substring(start,res.length() -end);
    }
    public static String RegexFind(String regex,String string){
        return RegexFind(regex, string, 1, 1);
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
        Log.d(mContext.getString(R.string.text_debug_data), text);
    }
    public static String getImageHtml(){
        return "<head><style>img{max-width: 320px !important;}</style></head>";
    }

    public static int getCurrentLanguage() {
        int lang = Settings.getInstance().getInt(Settings.LANGUAGE, -1);
        if (lang == -1) {
            String language = Locale.getDefault().getLanguage();
            String country = Locale.getDefault().getCountry();

            if (language.equalsIgnoreCase("zh")) {
                if (country.equalsIgnoreCase("CN")) {
                    lang = 1;
                } else {
                    lang = 2;
                }
            } else {
                lang = 0;
            }
        }
        return lang;
    }
    public static void clearCache(){

        DatabaseHelper mHelper = DatabaseHelper.instance(mContext);
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.execSQL(mHelper.DROP_TABLE + DailyTable.NAME);
        db.execSQL(DailyTable.CREATE_TABLE);

        db.execSQL(mHelper.DROP_TABLE + NewsTable.NAME);
        db.execSQL(NewsTable.CREATE_TABLE);

        db.execSQL(mHelper.DROP_TABLE + ReadingTable.NAME);
        db.execSQL(ReadingTable.CREATE_TABLE);

        db.execSQL(mHelper.DROP_TABLE + ScienceTable.NAME);
        db.execSQL(ScienceTable.CREATE_TABLE);

    }
}
