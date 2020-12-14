package com.example.meteo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

// Enregistrer ou recuperer une ville
public class Preference {
    private static final String PREFERENCE_CITY = "city";
    private static SharedPreferences getPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // setter
    public static void setCity(Context context, String city){
        getPreference(context)
                .edit()
                .putString(PREFERENCE_CITY, city)
                .apply();
    }
    // Getter
    public static String getCity(Context context){
        return getPreference(context).getString(PREFERENCE_CITY,null);
    }

}
