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

package com.mummyding.app.leisure.database.table;

/**
 * Created by mummyding on 15-11-26.
 */
public class ReadingTable {
    public static final String NAME="reading_table";
    public static final String COLLECTION_NAME = "collection_reading_table";


    public static final String TITLE = "title";
    public static final String INFO = "info";
    public static final String IMAGE ="image";
    public static final String EBOOK_URL = "ebook_url";
    public static final String CATALOG = "catalog";
    public static final String AUTHOR_INTRO = "author_intro";
    public static final String SUMMARY="summary";
    public static final String CATEGORY = "category";
    public static final String IS_COLLECTED = "is_collected";

    public static final int ID_TITLE = 0;
    public static final int ID_INFO = 1;
    public static final int ID_IMAGE = 2;
    public static final int ID_EBOOK_URL = 3;
    public static final int ID_CATALOG = 4;
    public static final int ID_AUTHOR_INTRO = 5;
    public static final int ID_SUMMARY = 6;
    public static final int ID_CATEGORY =7;
    public static final int ID_IS_COLLECTED = 8;


    public static final String CREATE_TABLE = "create table "+NAME+
            "("+TITLE+" text,"+
            INFO+" text,"+
            IMAGE+" text,"+
            EBOOK_URL+" text,"+
            CATALOG +" text,"+
            AUTHOR_INTRO+"  text,"+
            SUMMARY+" text,"+
            CATEGORY+" text,"+
            IS_COLLECTED+" integer)";


    public static final String CREATE_COLLECTION_TABLE = "create table "+COLLECTION_NAME+
            "("+TITLE+" text,"+
            INFO+" text,"+
            IMAGE+" text,"+
            EBOOK_URL+" text,"+
            CATALOG +" text,"+
            AUTHOR_INTRO+"  text,"+
            SUMMARY+" text)";


    public static final String SELECT_ALL_FROM_COLLECTION = "select * from "+COLLECTION_NAME;

    public static final String SQL_INIT_COLLECTION_FLAG= "update "+NAME+
            " set "+IS_COLLECTED+" =1 where "+TITLE+" in ( select "+TITLE+
            " from "+COLLECTION_NAME+")";

    public static  String updateCollectionFlag(String title,int flag){
        return "update "+NAME+" set "+IS_COLLECTED+" ="+flag+" where "+
                TITLE+"=\'"+title+"\'";
    }
    public static String deleteCollectionFlag(String title){
        return "delete from "+COLLECTION_NAME+" where title=\'"+title+"\'";
    }
}
