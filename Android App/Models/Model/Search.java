package com.example.grantrowberry.fmclient.Models.Model;

import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.UI.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GrantRowberry on 4/17/17.
 */

public class Search {
    List<PersonEvents> personEventsList = ModelContainer.getInstance().getPersonEventsList();
    Util util = new Util();

    public List<SearchItem> search(String query){
        String q = query.toLowerCase();
        List<SearchItem> searchItems = searchPeople(q);
        searchItems.addAll(searchEvents(query));
        if(searchItems.size() == 0){
            SearchItem searchItem = new SearchItem();
            searchItem.setItemType("noResults");
            searchItem.setPrimaryText("¯\\_(ツ)_/¯");
            searchItem.setSecondaryText("No Results Found");
            searchItems.add(searchItem);
        }
        return searchItems;

    }

    private List<SearchItem> searchPeople(String query){
        List<SearchItem> searchItems = new ArrayList<>();

        for (PersonEvents pE: personEventsList) {
            if(util.checkPerson(pE)) {
                StringBuilder infoStringBuilder = new StringBuilder();
                infoStringBuilder.append(pE.getPerson().getFirstName().toLowerCase());
                infoStringBuilder.append(pE.getPerson().getLastName().toLowerCase());
                if (infoStringBuilder.toString().contains(query)) {
                    SearchItem searchItem = new SearchItem();
                    searchItem.setPersonEvents(pE);
                    searchItem.setItemType("person");
                    searchItem.setPrimaryText(pE.getPerson().getFirstName());
                    searchItem.setSecondaryText(pE.getPerson().getLastName());
                    searchItems.add(searchItem);
                }
            }
        }
        return searchItems;
    }
    private List<SearchItem> searchEvents(String query){
        List<SearchItem> searchItems = new ArrayList<>();

        for(PersonEvents pE: personEventsList){
            for (Event event : pE.getEvents()) {
                if (util.checkEvent(event)) {
                    StringBuilder infoStringBuilder = new StringBuilder();
                    infoStringBuilder.append(event.getEventType().toLowerCase());
                    infoStringBuilder.append(event.getCity().toLowerCase());
                    infoStringBuilder.append(event.getCountry().toLowerCase());
                    infoStringBuilder.append(event.getYear());
                    if (infoStringBuilder.toString().contains(query)) {
                        SearchItem searchItem = new SearchItem();
                        searchItem.setPersonEvents(pE);
                        searchItem.setEvent(event);
                        searchItem.setItemType("event");
                        searchItem.setPrimaryText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
                        searchItem.setSecondaryText(pE.getPerson().getFirstName() + " " + pE.getPerson().getLastName());
                        searchItems.add(searchItem);
                    }

                }
            }
        }
        return searchItems;
    }
}
