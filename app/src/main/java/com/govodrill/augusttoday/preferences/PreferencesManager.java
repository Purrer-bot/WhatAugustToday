package com.govodrill.augusttoday.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import static com.govodrill.augusttoday.preferences.ConstantsPreferences.APP_PREFERENCES;

public class PreferencesManager {
    private Context context;
    public PreferencesManager(Context context){
        this.context = context;
    }
    /*May add more setters and getters
    * DEFAULT FOR BOOLEAN = TRUE
    * */
    public void set(String key, boolean value){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key){
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }
}
