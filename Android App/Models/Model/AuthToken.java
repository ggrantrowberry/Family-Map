package com.example.grantrowberry.fmclient.Models.Model;

import java.util.UUID;

/**
 * Created by GrantRowberry on 2/13/17.
 */

public class AuthToken {
    /**
     * Holds an authorization token and user ID.
     */

    String authtoken;
    String username;
    String timestamp;


    public AuthToken() {
        authtoken = UUID.randomUUID().toString();
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

