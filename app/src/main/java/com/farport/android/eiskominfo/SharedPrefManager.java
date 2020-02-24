package com.farport.android.eiskominfo;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "sidosharedpref";
    private static final String KEY_STATE = "keystate";
    private static final String KEY_NAMA_USER = "keynamauser";
    private static final String KEY_JABATAN = "keyjabatan";
    private static final String KEY_SKPD = "keyskpd";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_NO_HP = "keynohp";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_NAMA_USER, user.getNama_user());
        editor.putString(KEY_JABATAN, user.getJabatan());
        editor.putString(KEY_SKPD, user.getSkpd());
        editor.putString(KEY_NO_HP, user.getNo_hp());
        editor.putInt(KEY_STATE, user.getState());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_NAMA_USER, null),
                sharedPreferences.getString(KEY_JABATAN, null),
                sharedPreferences.getString(KEY_SKPD, null),
                sharedPreferences.getString(KEY_NO_HP, null),
                sharedPreferences.getInt(KEY_STATE, -1)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

