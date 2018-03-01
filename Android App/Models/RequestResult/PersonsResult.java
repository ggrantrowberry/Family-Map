package com.example.grantrowberry.fmclient.Models.RequestResult;

import java.util.List;

import com.example.grantrowberry.fmclient.Models.Model.*;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class PersonsResult {
    /**
     * A PersonsResult holds an array of Person objects populated from a user's family.
     */

    private List<Person> persons;
    /**
     * Message comes from error.
     */


    public List<Person> getData() {
        return persons;
    }

    public void setData(List<Person> data) {
        this.persons = data;
    }


}
