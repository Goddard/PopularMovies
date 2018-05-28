package com.goddardlabs.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.goddardlabs.popularmovies.R;

public final class Preferences {
    public String getSortType(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String key_sort_type = context.getString(R.string.pref_sort_key);
        String default_sort_type = context.getString(R.string.pref_sort_default);

        return sp.getString(key_sort_type, default_sort_type);
    }

    public void setSortType(Context context, String sort_type) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(context.getString(R.string.pref_sort_key), sort_type);
        editor.apply();
    }
}
