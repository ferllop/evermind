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
        String name = (String) map.get(UserField.NAME.getValue());
        String username = (String) map.get(UserField.USERNAME.getValue());
        String email = (String) map.get(UserField.EMAIL.getValue());
        String password = (String) map.get(UserField.PASSWORD.getValue());
        String status = (String) map.get(UserField.STATUS.getValue());
        Timestamp lastConnection = (Timestamp) map.get(UserField.LAST_CONNECTION.getValue());
        Timestamp signIn = (Timestamp) map.get(UserField.SIGN_IN.getValue());
        List<String> subscriptions = (List<String>) map.get(UserField.SUBSCRIPTIONS.getValue());
        return new User(id, name, username, email, password, UserStatus.valueOf(status), lastConnection, signIn, subscriptions);
    }

    @Override
    public Map<String, Object> execute(User user) {
        Map<String, Object> result = new HashMap<>();
        result.put(UserField.NAME.getValue(), user.getName());
        result.put(UserField.USERNAME.getValue(), user.getUsername());
        result.put(UserField.EMAIL.getValue(), user.getEmail());
        result.put(UserField.PASSWORD.getValue(), user.getPassword());
        result.put(UserField.STATUS.getValue(), user.getStatus().name());
        result.put(UserField.LAST_CONNECTION.getValue(), user.getLastConnection());
        result.put(UserField.SIGN_IN.getValue(), user.getSignIn());
        result.put(UserField.SUBSCRIPTIONS.getValue(), user.getSubscriptionsID());
        return result;
    }
}
