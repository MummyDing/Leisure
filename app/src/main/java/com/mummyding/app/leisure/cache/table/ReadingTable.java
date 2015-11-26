package com.mummyding.app.leisure.cache.table;

/**
 * Created by mummyding on 15-11-26.
 */
public class ReadingTable {
    public static final String NAME="reading_table";
    public static final String COLLECTION_NAME = "collection_reading_table";


    public static final String TITLE = "title";
    public static final String INFO = "info";
    public static final String IMAGE ="image";
    public static final String IS_COLLECTED = "is_collected";

    public static final int ID_TITLE = 0;
    public static final int ID_INFO = 1;
    public static final int ID_IMAGE = 2;
    public static final int ID_IS_COLLETED = 3;


    public static final String CREATE_TABLE = "create table "+NAME+
            "("+TITLE+" text primary key,"+
            INFO+" text,"+
            IMAGE+" text,"+
            IS_COLLECTED+" integer)";


    public static final String CREATE_COLLECTION_TABLE = "create table "+COLLECTION_NAME+
            "("+TITLE+" text primary key,"+
            INFO+" text,"+
            IMAGE+" text)";

    public static final String SQL_INIT_COLLECTION_FLAG= "update "+NAME+
            " set "+IS_COLLECTED+" =1 where "+TITLE+" in ( select "+TITLE+
            " from "+COLLECTION_NAME+")";

    public static  String updateCollectionFlag(String title,int flag){
        return "update "+NAME+" set "+IS_COLLECTED+" ="+flag+" where "+
                TITLE+"="+title;
    }
    public static String deleteCollectionFlag(String title){
        return "delete from "+COLLECTION_NAME+" where title="+title;
    }
}
