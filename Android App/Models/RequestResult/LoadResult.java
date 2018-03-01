package com.example.grantrowberry.fmclient.Models.RequestResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class LoadResult {
    /**
     * Holds a message about the success of the load.
     *
     * Message will either be from an error with the description or a success notification including x users y persons
     * and z events were added to the database.
     */

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
