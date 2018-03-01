package com.example.grantrowberry.fmclient.Models.Model;

import java.io.Serializable;

/**
 * Created by GrantRowberry on 4/17/17.
 */

public class SearchItem implements Serializable {
    private String itemType;
    private PersonEvents personEvents;
    private String primaryText;
    private String secondaryText;
    private Event event;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public PersonEvents getPersonEvents() {
        return personEvents;
    }

    public void setPersonEvents(PersonEvents personEvents) {
        this.personEvents = personEvents;
    }

    public String getPrimaryText() {
        return primaryText;
    }

    public void setPrimaryText(String primaryText) {
        this.primaryText = primaryText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
