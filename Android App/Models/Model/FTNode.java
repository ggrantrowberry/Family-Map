package com.example.grantrowberry.fmclient.Models.Model;

/**
 * Created by GrantRowberry on 4/7/17.
 */

public class FTNode {
    private PersonEvents personEvents = new PersonEvents();
    private int generation;
    private FTNode fatherNode;
    private FTNode motherNode;
    private String side;
    private FTNode childNode;

    public FTNode getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(FTNode fatherNode) {
        this.fatherNode = fatherNode;
    }

    public FTNode getMotherNode() {
        return motherNode;
    }

    public void setMotherNode(FTNode motherNode) {
        this.motherNode = motherNode;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public PersonEvents getPersonEvents() {
        return personEvents;
    }

    public void setPersonEvents(PersonEvents personEvents) {
        this.personEvents = personEvents;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public FTNode getChildNode() {
        return childNode;
    }

    public void setChildNode(FTNode childNode) {
        this.childNode = childNode;
    }
}
