package com.example.grantrowberry.fmclient.Models.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by GrantRowberry on 4/2/17.
 */

public class PersonEvents implements Serializable{
    private Person person = new Person();
    private List<Event> events = new ArrayList<>();
    private String familySide;

    //TODO: in server make sure dates are more accurate

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        events.add(event);
    }

    public void sortEvents(){
        Collections.sort(events);
    }

    public String getFamilySide() {
        return familySide;
    }

    public void setFamilySide(String familySide) {
        this.familySide = familySide;
    }

    //    @Override
//    public int hashCode() {
//        return person.getLastName().length() * person.getFirstName().length();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if(o == null){
//            return false;
//        }
//        if(o == this){
//            return true;
//        }
//        if(((PersonEvents)o).getPerson().getPersonID().equals(this.person.getPersonID())){
//            return true;
//        }
//        if(((Person)o).getPersonID().equals(this.person.getPersonID())){
//            return true;
//        }
//        if(o.getClass() != this.getClass()){
//            return false;
//        }
//        return false;
//    }

}
