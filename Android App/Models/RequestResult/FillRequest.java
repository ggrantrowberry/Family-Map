package com.example.grantrowberry.fmclient.Models.RequestResult;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class FillRequest {
    /**
     * Holds the information necessary to fill a users family tree.
     */
    private String username;
    private int generations;

    public FillRequest(){
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
