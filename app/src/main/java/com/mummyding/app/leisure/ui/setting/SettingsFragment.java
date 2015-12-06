package com.mummyding.app.leisure.ui.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.CompoundButton;

import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.Settings;
import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 15-12-6.
 * GitHub: https://github.com/MummyDing/
 * Blog: http://blog.csdn.net/mummyding
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener{
    private Settings mSettings;



    private String LANGUAGE = "language";
    private String AUTO_REFRESH = "auto_refresh";
    private String NIGHTMODE = "night_mode";
    private String SHAKE_TO_RETURN = "shake_to_return";
    private String NO_PIC_MODE = "no_pic_mode";
    private String CLEAR_CACHE = "clear_cache";

    private Preference mLanguage;
    private CheckBoxPreference mAutoRefresh;
    private CheckBoxPreference mNightMode;
    private CheckBoxPreference mShakeToReturn;
    private CheckBoxPreference mNoPicMode;
    private Preference mClearCache ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        mSettings = Settings.getInstance();

        mLanguage = findPreference(LANGUAGE);
        mAutoRefresh = (CheckBoxPreference) findPreference(AUTO_REFRESH);
        mNightMode = (CheckBoxPreference) findPreference(NIGHTMODE);
        mShakeToReturn = (CheckBoxPreference) findPreference(SHAKE_TO_RETURN);
        mNoPicMode = (CheckBoxPreference) findPreference(NO_PIC_MODE);
        mClearCache = findPreference(CLEAR_CACHE);

        mLanguage.setSummary(this.getResources().getStringArray(R.array.langs)[Utils.getCurrentLanguage()]);
        mAutoRefresh.setChecked(mSettings.getBoolean(mSettings.AUTO_REFRESH, false));
        mNightMode.setChecked(mSettings.getBoolean(mSettings.NIGTH_MODE, false));
        mShakeToReturn.setChecked(mSettings.getBoolean(mSettings.SHAKE_TO_RETURN, true));
        mNoPicMode.setChecked(mSettings.getBoolean(mSettings.NO_PIC_MODE, false));


        mAutoRefresh.setOnPreferenceChangeListener(this);
        mNightMode.setOnPreferenceChangeListener(this);
        mShakeToReturn.setOnPreferenceChangeListener(this);
        mNoPicMode.setOnPreferenceChangeListener(this);


        mLanguage.setOnPreferenceClickListener(this);
        mClearCache.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference == mAutoRefresh){
            mSettings.putBoolean(mSettings.AUTO_REFRESH,Boolean.valueOf(newValue.toString()));
            return true;
        }else if(preference == mNightMode){
            mSettings.putBoolean(mSettings.NIGTH_MODE,Boolean.valueOf(newValue.toString()));
            return true;
        }else if(preference == mShakeToReturn){
            mSettings.putBoolean(mSettings.SHAKE_TO_RETURN,Boolean.valueOf(newValue.toString()));
            return true;
        }else if(preference == mNoPicMode){
            mSettings.putBoolean(mSettings.NO_PIC_MODE,Boolean.valueOf(newValue.toString()));
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == mLanguage){
            showLangDialog();
        }else if(preference == mClearCache){
            Utils.clearCache();
            Snackbar.make(getView(), R.string.text_clear_cache_successful,Snackbar.LENGTH_SHORT).show();
        }
        return false;
    }

    private void showLangDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.text_language))
                .setSingleChoiceItems(
                        getResources().getStringArray(R.array.langs), Utils.getCurrentLanguage(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSettings.putInt(Settings.LANGUAGE, which);
                                dialog.dismiss();
                                getActivity().recreate();
                            }
                        }
                )
                .show();

    }
}
