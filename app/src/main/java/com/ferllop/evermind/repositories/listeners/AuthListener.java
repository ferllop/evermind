package com.ferllop.evermind.repositories.listeners;

import com.ferllop.evermind.repositories.fields.AuthError;

public interface AuthListener {
    void onRegister(String uid);
    void onLogin(String uid);
    void onError(AuthError error);
    void onAuthActionResult(AuthMessage message);
}
