package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.Model;

public interface DatastoreListener<T extends Model> {
    void onLoad(T item);
    void onDelete();
    void onError(String error);
    void onSave();
}
