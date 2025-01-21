package com.example.croudsourcing;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreference = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void saveSession(String email){
        editor.putString("email", email).commit();
    }

    public String getSession(){
        return sharedPreference.getString("email", "");
    }

}
