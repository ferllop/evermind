package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.User;

public interface UserRepository {
    void insert(User user);
    void load(String id);
    void getAll();
    void update(String id, User user);
    void delete(String id);
    void getFromUniqueField(String field, String value);
}
