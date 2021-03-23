package com.ferllop.evermind.repositories.listeners;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.repositories.fields.DataStoreError;

import java.util.List;

public interface UserDataStoreListener extends CrudDataStoreListener<User> {
    void onLoad(User user);
    void onDelete(String id);
    void onError(DataStoreError error);
    void onSave(User user);
    void onLoadAll(List<User> user);
    void usernameExists(boolean exist);
    void emailExists(boolean exist);
    void onAuthActionResult(AuthMessage message);
}
