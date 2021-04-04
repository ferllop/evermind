package com.ferllop.evermind.models;

import com.google.firebase.Timestamp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User extends Model{
    String authID;
    String name;
    String username;
    String email;
    UserStatus status;
    Timestamp lastLogin;
    Timestamp lastConnection;
    Timestamp signIn;
    int dayStartTime;

    public User(String authID, String name, String username, String email,
                UserStatus status, Timestamp lastLogin, Timestamp lastConnection, Timestamp signIn, int dayStartTime) {
        this.authID = authID;
        this.name = name;
        this.username = username;
        this.email = email;
        this.status = status;
        this.lastLogin = lastLogin;
        this.lastConnection = lastConnection;
        this.signIn = signIn;
        this.dayStartTime = dayStartTime;
    }

    public User(String id, String authID, String name, String username, String email,
                UserStatus status, Timestamp lastLogin, Timestamp lastConnection, Timestamp signIn, int dayStartTime) {
        this(authID, name, username, email, status, lastLogin, lastConnection, signIn, dayStartTime);
        this.setId(id);
    }

    public String getAuthID() {
        return authID;
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

    public UserStatus getStatus() {
        return status;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public Timestamp getLastConnection() {
        return lastConnection;
    }

    public Timestamp getSignIn() {
        return signIn;
    }

    public int getDayStartTime() {
        return dayStartTime;
    }

    public String encrypt(String text) throws NoSuchAlgorithmException {
        byte[] message = text.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(message);
        return String.format("%032X", new BigInteger(1, digest));
    }
}
