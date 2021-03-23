package com.ferllop.evermind.repositories.datastores;

public interface AuthDataStore {
    void register(String email, String password);
    void login(String email, String password);
    void logout();
    void sendVerificationEmailToCurrentUser();
    boolean isUserVerified();

    void sendResetPasswordEmail(String email);
}
