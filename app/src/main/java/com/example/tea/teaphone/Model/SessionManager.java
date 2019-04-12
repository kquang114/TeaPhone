package com.example.tea.teaphone.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    String LOGIN_KEY = "isLogin";
    String USERNAME_KEY = "user";
    String IDACCOUNT_KEY = "id";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoginState (boolean isLogin) {
        editor.putBoolean(LOGIN_KEY, isLogin);
        editor.commit();
    }
    public boolean getLoginState () {
        return preferences.getBoolean(LOGIN_KEY, false);
    }
    public void setUserName(String username) {
        editor.putString(USERNAME_KEY, username);
        editor.commit();
    }
    public String getUserName() {
        return preferences.getString(USERNAME_KEY, "");
    }
    public void setIDAccount(String id) {
        editor.putString(IDACCOUNT_KEY, id);
        editor.commit();
    }
    public String getIDAccount() {
        return preferences.getString(IDACCOUNT_KEY, "");
    }
}
