package com.example.grantrowberry.fmclient.Models.Model;

import android.util.Log;

import com.example.grantrowberry.fmclient.Models.ModelContainer;

import java.util.List;

/**
 * Created by GrantRowberry on 4/7/17.
 */

public class FamilyTree {
    private FTNode root = new FTNode();
    private List<PersonEvents> pEList = ModelContainer.getInstance().getPersonEvents();
    private int currentGeneration = 0;
    private int totalGenerations = 0;
    private int testNodesTouched =0;
    private FTNode nodeToReturn;

    public void findRoot(){
        for (PersonEvents personEvents: pEList) {
            if(personEvents.getPerson().getPersonID().equals(ModelContainer.getInstance().getUserId())){
                root.setPersonEvents(personEvents);
                root.setGeneration(0);
            }
        }
    }

    public void fillTree(){
        findRoot();
        fillTreeHelper(root);
        fillSides();
    }

    private void fillTreeHelper(FTNode node) {
        currentGeneration++;

        FTNode fatherNode = new FTNode();
        FTNode motherNode = new FTNode();

        if(node.getPersonEvents().getPerson().getFather() == null){
            if(currentGeneration > totalGenerations){
                totalGenerations = currentGeneration;
            }
            return;
        }

        if(node.getPersonEvents().getPerson().getMother() == null){
            if(currentGeneration > totalGenerations){
                totalGenerations = currentGeneration;
            }
            return;
        }

        for (PersonEvents personEvents: pEList) {
            if (personEvents.getPerson().getPersonID().equals(node.getPersonEvents().getPerson().getFather())) {
                fatherNode.setGeneration(currentGeneration);
                fatherNode.setPersonEvents(personEvents);
                fatherNode.setChildNode(node);
                node.setFatherNode(fatherNode);
            }
            if (personEvents.getPerson().getPersonID().equals(node.getPersonEvents().getPerson().getMother())) {
                motherNode.setGeneration(currentGeneration);
                motherNode.setPersonEvents(personEvents);
                motherNode.setChildNode(node);
                node.setMotherNode(motherNode);
            }

        }

        fillTreeHelper(fatherNode);
        currentGeneration--;
        fillTreeHelper(motherNode);
        currentGeneration--;

    }


    @Override

    public String toString(){
        StringBuilder builder = new StringBuilder();
        toStringHelper(builder, root);
        return builder.toString();
    }

    private void toStringHelper(StringBuilder builder, FTNode node) {
        if(node.getFatherNode() != null){
            Person p = node.getPersonEvents().getPerson();
            Person f = node.getFatherNode().getPersonEvents().getPerson();
            Person m = node.getMotherNode().getPersonEvents().getPerson();
            builder.append(p.getFirstName() + " " + p.getLastName());

            toStringHelper(builder, node.getFatherNode());
            toStringHelper(builder, node.getMotherNode());
        }

    }

    public FTNode getNode(String personID){
         nodeToReturn = new FTNode();
        getNodeHelper(root, personID);
        return nodeToReturn;
    }

    private void getNodeHelper(FTNode node, String personID){
        if(node.getPersonEvents().getPerson().getPersonID().equals(personID)){
            nodeToReturn = node;
            return;
        }
        if(node.getFatherNode() != null) {
            getNodeHelper(node.getFatherNode(),personID);
        }
        if(node.getMotherNode() != null) {
            getNodeHelper(node.getMotherNode(),personID);
        }
    }

    private void fillSides() {
        if(root.getFatherNode() != null) {
            fillSideHelper(root.getFatherNode(),"father");
        }
        if(root.getMotherNode() != null) {
            fillSideHelper(root.getMotherNode(), "mother");
        }
    }

    private void fillSideHelper(FTNode node, String side) {
        node.getPersonEvents().setFamilySide(side);

        if(node.getFatherNode() != null) {
            fillSideHelper(node.getFatherNode(), side);
        }

        if(node.getMotherNode() != null) {
            fillSideHelper(node.getMotherNode(), side);
        }

    }

    public int getTotalGenerations() {
        return totalGenerations;
    }

    public void setTotalGenerations(int totalGenerations) {
        this.totalGenerations = totalGenerations;
    }

    public FTNode getRoot() {
        return root;
    }

    public void setRoot(FTNode root) {
        this.root = root;
    }
}
