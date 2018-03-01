package com.example.grantrowberry.fmclient.Models.Model;

import android.content.Context;


import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GrantRowberry on 4/14/17.
 */

public class Util {

    public boolean checkEvent(Event event) {
        PersonEvents personEvents = findPerson(event.getPersonID());
        boolean isGood = true;

        if(! ModelContainer.getInstance().isFilterOn(personEvents.getPerson().getGender())) {
            isGood = false;
        }

        if(! ModelContainer.getInstance().isFilterOn(event.getEventType())) {
            isGood = false;
        }
        if(personEvents.getFamilySide() != null) {
            if (!ModelContainer.getInstance().isFilterOn(personEvents.getFamilySide())) {
                isGood = false;
            }
        }

        return isGood;
    }

    public boolean checkPerson(PersonEvents personEvents){
        boolean isGood = true;
        if(personEvents.getPerson().getPersonID().equals(ModelContainer.getInstance().getUserId())){
            return true;
        }
        if(! ModelContainer.getInstance().isFilterOn(personEvents.getFamilySide())){
            isGood = false;
        }


        return isGood;
    }

    public PersonEvents findPerson(String personID){
        List<PersonEvents> list = ModelContainer.getInstance().getPersonEvents();

        for (PersonEvents personEvent: list) {

            if(personEvent.getPerson().getPersonID().equals(personID)){
                return personEvent;
            }

        }

        return null;
    }

    public List<PersonRelationship> getRelationships(String personID) {
        List<PersonRelationship> list = new ArrayList<>();
        getRelationshipsHelper(ModelContainer.getInstance().getFamilyTree().getRoot(), personID, list);
        return list;
    }

    private void getRelationshipsHelper(FTNode node, String personID, List<PersonRelationship> list) {
        if(node.getPersonEvents().getPerson().getPersonID().equals(personID)){
            if(node.getFatherNode() != null) {
                PersonRelationship father = new PersonRelationship();
                father.setPerson(node.getFatherNode().getPersonEvents().getPerson());
                father.setRelationship("Father");
                list.add(father);
            }
            if(node.getMotherNode() != null) {
                PersonRelationship mother = new PersonRelationship();
                mother.setPerson(node.getMotherNode().getPersonEvents().getPerson());
                mother.setRelationship("Mother");
                list.add(mother);
            }
            if(node.getChildNode() != null) {
                PersonRelationship child = new PersonRelationship();
                child.setPerson(node.getChildNode().getPersonEvents().getPerson());
                child.setRelationship("Child");
                list.add(child);
            }
            if(node.getPersonEvents().getPerson().getSpouse() != null) {
                if(node.getPersonEvents().getPerson().getSpouse() != null) {
                    PersonRelationship spouse = new PersonRelationship();
                    spouse.setPerson(findPerson(node.getPersonEvents().getPerson().getSpouse()).getPerson());
                    spouse.setRelationship("Spouse");
                    list.add(spouse);
                }
            }
        } else {
            if(node.getFatherNode() != null) {
                getRelationshipsHelper(node.getFatherNode(),personID,list);
            }
            if(node.getMotherNode() != null) {
                getRelationshipsHelper(node.getMotherNode(),personID,list);
            }
        }

    }
    public void updateColor(String lineType, String color){
         Integer c =  ModelContainer.getInstance().getLineColors().get(color);
        if(lineType.equals("lifeStory")) {
            ModelContainer.getInstance().setLifeStoryColor(c);
        } else if(lineType.equals("familyTree")){
            ModelContainer.getInstance().setFamilyTreeColor(c);
        } else if(lineType.equals("spouse")){
            ModelContainer.getInstance().setSpouseColor(c);
        }
    }
    public void fillColors(Context c) {
        String[] keys = c.getResources().getStringArray(R.array.color_keys);
        int[] values = c.getResources().getIntArray(R.array.color_values);
        LinkedHashMap<String,Integer> colors = new LinkedHashMap<>();

        for(int i = 0; i < Math.min(keys.length, values.length); ++i){
            colors.put(keys[i], values[i]);
        }

        ModelContainer.getInstance().setLineColors(colors);
        ModelContainer.getInstance().setLifeStoryColor(ModelContainer.getInstance().getLineColors().get("Yellow"));
        ModelContainer.getInstance().setFamilyTreeColor(ModelContainer.getInstance().getLineColors().get("Light Blue"));
        ModelContainer.getInstance().setSpouseColor(ModelContainer.getInstance().getLineColors().get("Orange Red"));
        ModelContainer.getInstance().setEventColors();

    }
    public String[] getMapTypes() {
        List<String> types = new ArrayList<>();
        for (Map.Entry<String,Integer> entry: ModelContainer.getInstance().getMapTypes().entrySet()) {
            types.add(entry.getKey());
        }

        String[] typeArray = new String[types.size()];
        typeArray = types.toArray(typeArray);
        return typeArray;
    }

    public void setMapType(String mapType){
        Integer type = ModelContainer.getInstance().getMapTypes().get(mapType);
        ModelContainer.getInstance().setCurrentMapType(type);
    }

    public int getMapTypeIndex() {
        int i = 0;
        for (Map.Entry<String, Integer> entry: ModelContainer.getInstance().getMapTypes().entrySet()) {
            if(entry.getValue().equals(ModelContainer.getInstance().getCurrentMapType())){
                return i;
            }
            ++i;
        }
        return 0;
    }

    public int getColorIndex(String lineType) {
        int colorValue = 0;
        if(lineType.equals("lifeStory")){
            colorValue = ModelContainer.getInstance().getLifeStoryColor();
        }
        if(lineType.equals("familyTree")){
            colorValue = ModelContainer.getInstance().getFamilyTreeColor();
        }
        if(lineType.equals("spouse")){
            colorValue = ModelContainer.getInstance().getSpouseColor();
        }
        int i = 0;
        for(Map.Entry<String,Integer> entry: ModelContainer.getInstance().getLineColors().entrySet()){
            if(entry.getValue().equals(colorValue)){
                return i;
            }
            ++i;
        }
        return i;
    }

    public Integer getMarkerColor(String eventType){
       return ModelContainer.getInstance().getEventColors().get(eventType);
    }

    public void makeEventTypesUniform(){
        for (PersonEvents pe: ModelContainer.getInstance().getPersonEvents()) {
            for(Event e: pe.getEvents()){
                    e.setEventType(e.getEventType().toLowerCase());
                    String s1 = e.getEventType().substring(0,1).toUpperCase();
                    e.setEventType(s1 + e.getEventType().substring(1));
            }

        }
    }

    public Boolean isUser(String personId){
        if(personId.equals(ModelContainer.getInstance().getUserId())){
            return true;
        }
        return false;
    }

}
