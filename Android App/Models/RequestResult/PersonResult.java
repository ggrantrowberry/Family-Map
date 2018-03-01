package com.example.grantrowberry.fmclient.Models.RequestResult;

import com.example.grantrowberry.fmclient.Models.Model.Person;

/**
 * Created by GrantRowberry on 2/15/17.
 */

public class PersonResult {

    /**
     * Holds all the information about a person that is gathered from the database.
     */
    private String descendant; // Name of user account this person belongs to
    private String personID;   // Person’s unique ID
    private String firstName;  // Person’s first name
    private String lastName;  // Person’s last name
    private String gender;  // Person’s gender (“m” or “f”)
    private String father;  // ID of person’s father  [OPTIONAL, can be missing]
    private String mother;  // ID of person’s mother  [OPTIONAL, can be missing]
    private String spouse;  // ID of person’s spouse  [OPTIONAL, can be missing]
    /**
     * Message would come from an error.
     */
    private String message;


    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Person makePersonObject(){
        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setGender(gender);
        p.setDescendant(descendant);
        p.setFather(father);
        p.setMother(mother);
        p.setPersonID(personID);
        p.setSpouse(spouse);
        return p;
    }
}
