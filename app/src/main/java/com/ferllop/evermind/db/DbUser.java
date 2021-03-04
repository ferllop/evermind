package com.ferllop.evermind.db;

public interface DbUser {
    void onLoad(ModelDao dao);
    void onSave();
    void onDelete();
    void onError(String errorMessage);
}
