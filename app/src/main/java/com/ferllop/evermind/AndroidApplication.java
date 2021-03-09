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
    }

    private void setFixedAuthor(){
        SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.general_preferences_id), Context.MODE_PRIVATE).edit();
        prefs.putString("user", "ferllop").putString("userID", "Q8tMRaXVhVm0GGvCGiuc").commit();
    }

    public static String getUser(Context activity){
        return activity.getSharedPreferences(activity.getString(R.string.general_preferences_id), Context.MODE_PRIVATE)
                .getString("user", "anonymous");
    }

    public static String getUserID(Context activity){
        return activity.getSharedPreferences(activity.getString(R.string.general_preferences_id), Context.MODE_PRIVATE)
                .getString("userID", "none");
    }

}
