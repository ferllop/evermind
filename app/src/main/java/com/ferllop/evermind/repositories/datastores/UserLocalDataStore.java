package com.ferllop.evermind.repositories.datastores;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.fields.UserField;

public class UserLocalDataStore {

    private final String TAG = "MYAPP-local";

    String prefix = "user";
    Context context;

    public UserLocalDataStore(Context context) {
        this.context = context;
    }

    public void setUser(User user){
        setID(user.getId());
        setUsername(user.getUsername());
        setName(user.getName());
        setDayStartTime(user.getDayStartTime());
    }

    public String getName(){
        return getStringFromShared(UserField.NAME.getValue());
    }

    public void setName(String name) {
        setStringFromShared(UserField.NAME.getValue(), name);
    }

    public String getDayStartTime(){
        return getStringFromShared(UserField.DAY_START_TIME.getValue());
    }

    public void setDayStartTime(int time) {
        setStringFromShared(UserField.DAY_START_TIME.getValue(), String.valueOf(time));
    }

    public String getID(){
        return getStringFromShared(UserField.USER_ID.getValue());
    }

    public void setID(String userID){
        setStringFromShared(UserField.USER_ID.getValue(), userID);
    }

    public String getUsername(){
        return getStringFromShared(UserField.USERNAME.getValue());
    }

    public void setUsername(String username){
        setStringFromShared(UserField.USERNAME.getValue(), username);
    }

    public void clear(){
        Log.d(TAG, "clear: ");
        getPrefs().edit().clear().apply();
    }

    private String getStringFromShared(String key){
        return getPrefs().getString(prefix + "-" + key, null);
    }

    private void setStringFromShared(String key, String value){
        getPrefs().edit().putString(prefix + "-" + key, value).apply();
    }

    private SharedPreferences getPrefs(){
        return context.getSharedPreferences(context.getString(R.string.logged_user_data), Context.MODE_PRIVATE);
    }
}
