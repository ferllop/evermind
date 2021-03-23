package com.ferllop.evermind.repositories.mappers;

import com.ferllop.evermind.models.User;
import com.ferllop.evermind.models.UserStatus;
import com.ferllop.evermind.repositories.fields.UserField;
import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper implements ModelMapper<User> {
    @Override
    public User execute(String id, Map<String, Object> map) {
        String authID = (String) map.get(UserField.AUTH_ID.getValue());
        String name = (String) map.get(UserField.NAME.getValue());
        String username = (String) map.get(UserField.USERNAME.getValue());
        String email = (String) map.get(UserField.EMAIL.getValue());
        String status = (String) map.get(UserField.STATUS.getValue());
        Timestamp lastLogin = (Timestamp) map.get(UserField.LAST_LOGIN.getValue());
        Timestamp lastConnection = (Timestamp) map.get(UserField.LAST_CONNECTION.getValue());
        Timestamp signIn = (Timestamp) map.get(UserField.SIGN_IN.getValue());
        return new User(id, authID, name, username, email, UserStatus.valueOf(status), lastLogin, lastConnection, signIn);
    }

    @Override
    public Map<String, Object> execute(User user) {
        Map<String, Object> result = new HashMap<>();
        result.put(UserField.AUTH_ID.getValue(), user.getAuthID());
        result.put(UserField.NAME.getValue(), user.getName());
        result.put(UserField.USERNAME.getValue(), user.getUsername());
        result.put(UserField.EMAIL.getValue(), user.getEmail());
        result.put(UserField.STATUS.getValue(), user.getStatus().name());
        result.put(UserField.LAST_LOGIN.getValue(), user.getLastLogin());
        result.put(UserField.LAST_CONNECTION.getValue(), user.getLastConnection());
        result.put(UserField.SIGN_IN.getValue(), user.getSignIn());
        return result;
    }
}
