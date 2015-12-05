package com.mummyding.app.leisure;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mummyding.app.leisure.model.science.ScienceBean;

/**
 * Created by mummyding on 15-11-13.
 */
public class LeisureApplication extends Application {
    public static Context AppContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
        Fresco.initialize(AppContext);
    }
}
