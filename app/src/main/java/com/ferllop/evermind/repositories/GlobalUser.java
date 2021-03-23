package com.ferllop.evermind.repositories;

import com.ferllop.evermind.models.User;

public class GlobalUser {

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

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void clear(){
        user = null;
    }
}
