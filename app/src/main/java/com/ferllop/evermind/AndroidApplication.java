package com.ferllop.evermind;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ferllop.evermind.activities.HomeActivity;
import com.ferllop.evermind.activities.LoginActivity;
import com.ferllop.evermind.repositories.AuthRepository;
import com.ferllop.evermind.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class AndroidApplication extends Application {

    final String TAG = "MYAPP-App";
    @Override
    public void onCreate(){
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override public void onActivityStarted(@NonNull Activity activity) {}

            @Override public void onActivityResumed(@NonNull Activity activity) {}

            @Override public void onActivityPaused(@NonNull Activity activity) {}

            @Override public void onActivityStopped(@NonNull Activity activity) {}

            @Override public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

            @Override public void onActivityDestroyed(@NonNull Activity activity) {}

        });



        //Log.d(TAG, "onCreate: " + new AuthRepository(null).isLoggedIn());
        // FirebaseAuth.getInstance().signOut();

        //setFixedAuthor();

    }



}
