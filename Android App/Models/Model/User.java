package com.example.grantrowberry.fmclient.Models.Model;

/**
 * Created by GrantRowberry on 2/13/17.
 */

public class User {

    /**
     * Holds the information of a user.
     */
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public String getPassword() {

        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUsername() {

        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }
}
