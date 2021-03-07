package com.ferllop.evermind;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class AndroidApplication extends Application {


    @Override
    public void onCreate(){
        super.onCreate();
        setFixedAuthor();
      Log.d("ARRANCA", "si");
    }

    private void setFixedAuthor(){
        SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.general_preferences_id), Context.MODE_PRIVATE).edit();
        prefs.putString("user", "ferllop").commit();
    }

    public static String getUser(Activity activity){
        return activity.getSharedPreferences(activity.getString(R.string.general_preferences_id), Context.MODE_PRIVATE)
                .getString("user", "anonymous");
    }

}
