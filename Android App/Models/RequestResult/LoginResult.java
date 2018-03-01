package com.example.grantrowberry.fmclient.Models.RequestResult;


/**
 * Created by GrantRowberry on 2/15/17.
 */

public class LoginResult {
    /**
     * Holds the desired information that comes after logging a user in.
     */
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
