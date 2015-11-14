package com.mummyding.app.leisure;

import android.app.Application;
import android.content.Context;

/**
 * Created by mummyding on 15-11-13.
 */
public class LeisureApplication extends Application {
    public static Context AppContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
    }
}
