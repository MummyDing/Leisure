package com.mummyding.app.leisure.support;

import android.content.Context;
import android.content.SharedPreferences;

import com.mummyding.app.leisure.LeisureApplication;

/**
 * Created by mummyding on 15-12-6.
 * GitHub: https://github.com/MummyDing/
 * Blog: http://blog.csdn.net/mummyding
 */
public class Settings {

    public static  boolean needRecreate = false;

    public static final String XML_NAME = "settings";

    public static final String SHAKE_TO_RETURN = "shake_to_return";

    public static final String NO_PIC_MODE = "no_pic_mode";

    public static final String NIGHT_MODE = "night_mode";

    public static final String AUTO_REFRESH = "auto_refresh";

    public static final String LANGUAGE = "language";

    public static final String EXIT_CONFIRM = "exit_confirm";


    private static Settings sInstance;

    private SharedPreferences mPrefs;

    public static Settings getInstance() {
        if (sInstance == null) {
            sInstance = new Settings(LeisureApplication.AppContext);
        }
        return sInstance;
    }

    private Settings(Context context) {
        mPrefs = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
    }

    public Settings putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }

    public Settings putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).commit();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public Settings putString(String key, String value) {
        mPrefs.edit().putString(key, value).commit();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

}
