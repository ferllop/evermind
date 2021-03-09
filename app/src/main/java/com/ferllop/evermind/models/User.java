package com.ferllop.evermind.models;

import com.google.firebase.Timestamp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class User extends Model{
    String name;
    String username;
    String email;
    String password;
    UserStatus status;
    Timestamp lastConnection;
    Timestamp signIn;
    List<String> subscriptionsID;

    public User(String id, String name, String username, String email, String password,
                UserStatus status, Timestamp lastConnection, Timestamp signIn,
                List<String> subscriptionsID) {
        setId(id);
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.lastConnection = lastConnection;
        this.signIn = signIn;
        this.subscriptionsID = subscriptionsID;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Timestamp getLastConnection() {
        return lastConnection;
    }

    public Timestamp getSignIn() {
        return signIn;
    }

    public List<String> getSubscriptionsID() {
        return subscriptionsID;
    }

    public String encrypt(String text) throws NoSuchAlgorithmException {
        byte[] message = text.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(message);
        return String.format("%032X", new BigInteger(1, digest));
    }
}
