package com.example.grantrowberry.fmclient.Models.RequestResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class ClearResult {
    /**
     * Holds a message about the success of the clear.
     * Message could either be from an error or from a successful clearing of the database.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
