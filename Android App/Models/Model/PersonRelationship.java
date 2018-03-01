package com.example.grantrowberry.fmclient.Models.Model;

/**
 * Created by GrantRowberry on 4/17/17.
 */

public class PersonRelationship {
    Person person;
    String relationship;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
