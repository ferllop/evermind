package com.ferllop.evermind.repositories.otro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.fields.UserField;

public class GlobalUser {

    private final static String TAG = "MYAPP-GlobalUser";
    private User user;
    private static GlobalUser globalUser;

    private GlobalUser() {
    }

    public static GlobalUser getInstance(){
        if (globalUser == null){
            globalUser = new GlobalUser();
        }
        return globalUser;
    }

    public void setUser(Context activity, User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void clear(){
        user = null;
    }
}
