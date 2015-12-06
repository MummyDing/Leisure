package com.mummyding.app.leisure.ui.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mummyding.app.leisure.R;

/**
 * Created by mummyding on 15-12-6.
 * GitHub: https://github.com/MummyDing/
 * Blog: http://blog.csdn.net/mummyding
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
