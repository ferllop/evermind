package com.ferllop.evermind.db;

import com.ferllop.evermind.models.IdentifiedCard;

public interface DbUser {
    void onLoad(IdentifiedCard card);
    void onSave();
    void onDelete();
    void onError(String errorMessage);
}
