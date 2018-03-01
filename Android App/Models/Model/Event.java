package com.example.grantrowberry.fmclient.Models.Model;

import java.io.Serializable;

/**
 * Created by GrantRowberry on 2/13/17.
 */

public class Event implements Comparable<Event>, Serializable{
    /**
     * Holds the information about an Event.
     */

    private String eventID;
    private String descendant;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String person) {
        this.personID = person;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Event otherEvent){
        if(eventType.toLowerCase().equals("birth")){
            return -10000;
        }
        if(eventType.toLowerCase().equals("death")){
            return 10000;
        }
        if(year - otherEvent.getYear() != 0){
            return year - otherEvent.getYear();
        }

        int idDiff = eventID.compareTo(otherEvent.getEventID());
        return idDiff;
    }
}
