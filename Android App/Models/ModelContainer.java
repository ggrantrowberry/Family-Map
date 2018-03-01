package com.example.grantrowberry.fmclient.Models;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.example.grantrowberry.fmclient.Models.Model.AuthToken;
import com.example.grantrowberry.fmclient.Models.Model.Event;
import com.example.grantrowberry.fmclient.Models.Model.FamilyTree;
import com.example.grantrowberry.fmclient.Models.Model.FilterItem;
import com.example.grantrowberry.fmclient.Models.Model.Person;
import com.example.grantrowberry.fmclient.Models.Model.PersonEvents;
import com.example.grantrowberry.fmclient.Models.Model.Util;
import com.example.grantrowberry.fmclient.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by GrantRowberry on 3/23/17.
 */

public class ModelContainer {
    private AuthToken authToken;
    private List<Person> persons;
    private List<Event> events;
    private String serverHost;
    private String serverPort;
    private Person userPerson;
    private String userId;
    private Boolean loggedIn = false;
    private Map<Marker, Event> eventMarkers;
    private List<PersonEvents> personEventsList = new ArrayList<>();
    private List<FilterItem> filterItemsList = new ArrayList<>();
    private FamilyTree familyTree;
    private Set<String> eventTypes;
    private Boolean lifeStoryIsOn = true;
    private Boolean familyTreeIsOn = true;
    private Boolean spouseIsOn = true;
    private Integer lifeStoryColor;
    private Integer familyTreeColor;
    private Integer spouseColor;
    private Map<String, Integer> lineColors;
    private Map<String, Integer> mapTypes;
    private Integer currentMapType = AmazonMap.MAP_TYPE_SATELLITE;
    private Map<String, Integer> eventColors;


    private static ModelContainer instance = null;

    protected ModelContainer() {
        // Exists only to defeat instantiation.
    }

    public static ModelContainer getInstance() {
        if (instance == null) {
            instance = new ModelContainer();
        }
        return instance;
    }

    public List<PersonEvents> getPersonEventsList() {
        return personEventsList;
    }

    public void setPersonEventsList(List<PersonEvents> personEventsList) {
        this.personEventsList = personEventsList;
    }

    public List<FilterItem> getFilterItemsList() {
        return filterItemsList;
    }

    public void setFilterItemsList(List<FilterItem> filterItemsList) {
        this.filterItemsList = filterItemsList;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Person getUserPerson() {
        return userPerson;
    }

    public void setUserPerson(Person userPerson) {
        this.userPerson = userPerson;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Map<Marker, Event> getEventMarkers() {
        return eventMarkers;
    }

    public void setEventMarkers(Map<Marker, Event> eventMarkers) {
        this.eventMarkers = eventMarkers;
    }

    public List<PersonEvents> getPersonEvents() {
        return personEventsList;
    }

    public void setPersonEvents(List<PersonEvents> personEvents) {
        this.personEventsList = personEvents;
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    public void setEventTypes(Set<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Boolean getLifeStoryIsOn() {
        return lifeStoryIsOn;
    }

    public void setLifeStoryIsOn(Boolean lifeStoryIsOn) {
        this.lifeStoryIsOn = lifeStoryIsOn;
    }

    public Boolean getFamilyTreeIsOn() {
        return familyTreeIsOn;
    }

    public void setFamilyTreeIsOn(Boolean familyTreeIsOn) {
        this.familyTreeIsOn = familyTreeIsOn;
    }

    public Boolean getSpouseIsOn() {
        return spouseIsOn;
    }

    public void setSpouseIsOn(Boolean spouseIsOn) {
        this.spouseIsOn = spouseIsOn;
    }

    public Integer getLifeStoryColor() {
        return lifeStoryColor;
    }

    public void setLifeStoryColor(Integer lifeStoryColor) {
        this.lifeStoryColor = lifeStoryColor;
    }

    public Integer getFamilyTreeColor() {
        return familyTreeColor;
    }

    public void setFamilyTreeColor(Integer familyTreeColor) {
        this.familyTreeColor = familyTreeColor;
    }

    public Integer getSpouseColor() {
        return spouseColor;
    }

    public void setSpouseColor(Integer spouseColor) {
        this.spouseColor = spouseColor;
    }

    public Map<String, Integer> getLineColors() {
        return lineColors;
    }

    public void setLineColors(Map<String, Integer> lineColors) {
        this.lineColors = lineColors;
    }

    public Map<String, Integer> getMapTypes() {
        return mapTypes;
    }

    public void setMapTypes(Map<String, Integer> mapTypes) {
        this.mapTypes = mapTypes;
    }

    public Integer getCurrentMapType() {
        return currentMapType;
    }

    public void setCurrentMapType(Integer currentMapType) {
        this.currentMapType = currentMapType;
    }

    public Map<String, Integer> getEventColors() {
        return eventColors;
    }

    public void setEventColors(Map<String, Integer> eventColors) {
        this.eventColors = eventColors;
    }

    public void initializeData(){
        fillPersonEvents();
        Util util = new Util();
        util.makeEventTypesUniform();
        fillFilterItems();
        createFamilyTree();
        setMapTypes();
    }

    public void createFamilyTree(){
        familyTree = new FamilyTree();
        familyTree.fillTree();
    }

    public void fillPersonEvents() {
        for (Person person : persons) {
            PersonEvents personEvents = new PersonEvents();
            personEvents.setPerson(person);
            for (Event event : events) {
                if (person.getPersonID().equals(event.getPersonID())) {
                    String s = event.getCity();
                    personEvents.addEvent(event);

                }

            }
            personEvents.sortEvents();
            personEventsList.add(personEvents);
        }
    }

    public void  fillFilterItems() {
        Set<String> eventTypes = new HashSet<>();
        for (Event event: events) {
            eventTypes.add(event.getEventType());
        }
        setEventTypes(eventTypes);
        for (String s: eventTypes){
            String s1 = s.substring(0,1).toUpperCase();
            FilterItem filterItem = new FilterItem();
            filterItem.setFilterType(s);
            filterItem.setFilterTitle(s1 + s.substring(1)  + " Events");
            filterItem.setON(true);
            filterItemsList.add(filterItem);
        }
        FilterItem fatherFilter = new FilterItem();
        fatherFilter.setFilterTitle("Father's Side");
        fatherFilter.setFilterType("father");
        filterItemsList.add(fatherFilter);
        FilterItem motherFilter = new FilterItem();
        motherFilter.setFilterTitle("Mother's Side");
        motherFilter.setFilterType("mother");
        filterItemsList.add(motherFilter);
        FilterItem maleFilter = new FilterItem();
        maleFilter.setFilterTitle("Male Events");
        maleFilter.setFilterType("m");
        filterItemsList.add(maleFilter);
        FilterItem femaleFilter = new FilterItem();
        femaleFilter.setFilterTitle("Female Events");
        femaleFilter.setFilterType("f");
        filterItemsList.add(femaleFilter);
    }

    public Boolean isFilterOn (String type){
        for (FilterItem filterItem: filterItemsList) {
            if (filterItem.getFilterType().equals(type)) {
                return filterItem.getON();
            }
        }
        return null;
    }

    private void setMapTypes(){
        Map<String, Integer> types = new HashMap<>();
        types.put("Hybrid", AmazonMap.MAP_TYPE_HYBRID);
        types.put("Normal", AmazonMap.MAP_TYPE_NORMAL);
        types.put("Satellite", AmazonMap.MAP_TYPE_SATELLITE);
        types.put("Terrain", AmazonMap.MAP_TYPE_TERRAIN);
        mapTypes = types;
    }

    public void clear(){
        persons.clear();
        events.clear();
        personEventsList.clear();

    }

    public void setEventColors(){
        List<Integer> colors = new ArrayList<>();

        for (Map.Entry<String,Integer> entry: lineColors.entrySet()) {
            float[] hue = new float[3];
            ColorUtils.colorToHSL(entry.getValue(),hue);
            colors.add((int) hue[0]);
        }

        eventColors = new HashMap<>();
        int i = -1;
        for (String s: eventTypes) {
            ++i;
            if(i > colors.size()-1){
                i =0;
            }

            eventColors.put(s,colors.get(i));
        }
    }

    public void clearFilterItems(){
        filterItemsList.clear();
    }

    public void changeFilter(String filter, Boolean isOn){
        for (FilterItem filterItem: filterItemsList) {
            if(filterItem.getFilterType().equals(filter)){
                filterItem.setON(isOn);
            }
        }
    }
}

