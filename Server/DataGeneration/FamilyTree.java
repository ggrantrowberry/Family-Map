package DataGeneration;

import java.io.FileNotFoundException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import Model.*;


/**
 * Created by GrantRowberry on 3/6/17.
 */


public class FamilyTree {
    private PersonNode root = new PersonNode();
    private int totalGenerations = 4;
    private int currentGeneration = 0;
    private DataGenerator dg = new DataGenerator();

    public int getGenerations() {
        return totalGenerations;
    }

    public void setGenerations(int generations) {
        this.totalGenerations = generations;
    }

    public List<Event> getEvents(){
        List<Event> events = new ArrayList<>();
        eventHelper((ArrayList<Event>) events, root);
//        for (Event e : events) {
//
//            System.out.println(e.getEventID() + " " + e.getEventType() + " " +e.getCity() + " " +e.getCountry());
//        }
        return events;
    }

    void eventHelper(ArrayList<Event> events, PersonNode node){
        if(node != root) {
            events.addAll(node.events);
        }
        if(node.father != null){
            eventHelper(events, node.father);
        }
        if(node.mother != null){
            eventHelper(events, node.mother);
        }
    }
    public List<Person> getPerson(){
        List<Person> persons = new ArrayList<>();
        personHelper((ArrayList<Person>) persons, root);
        return persons;
    }

    void personHelper(ArrayList<Person> persons, PersonNode node){
        //if(node != root) {
            persons.add(node.person);
        //}
        if(node.father != null){
            personHelper(persons, node.father);
        }
        if(node.mother != null){
            personHelper(persons, node.mother);
        }
    }







    public void addGenerations(Person descendant) throws FileNotFoundException {
        dg.generate();
        root.person = descendant;

        addGenerationHelper(root);
        fillSurnames(root);
    }

    private void addGenerationHelper(PersonNode p){
        currentGeneration++;
        if(currentGeneration <= totalGenerations){
           // System.out.println(currentGeneration);
            Person father = new Person();
            Person mother = new Person();
            father.setGender("m");
            father.setDescendant(root.person.getDescendant());
            father.setFirstName(getMaleFirstName());
            String fatherID = UUID.randomUUID().toString();
            father.setPersonID(fatherID);
            mother.setGender("f");
            mother.setDescendant(root.person.getDescendant());
            mother.setFirstName(getFemaleFirstName());
            String motherID = UUID.randomUUID().toString();
            mother.setPersonID(motherID);
            father.setSpouse(motherID);
            mother.setSpouse(fatherID);
            Event fatherBirth = generateBirthEvent(fatherID);
            Event motherBirth = generateBirthEvent(motherID);
            father.setBirthYear(fatherBirth.getYear());
            mother.setBirthYear(motherBirth.getYear());

            p.father = new PersonNode();
            p.mother = new PersonNode();

            p.father.person = father;
            p.mother.person = mother;
            p.father.events.add(fatherBirth);
            p.mother.events.add(motherBirth);

            int baptismProbability = new Random().nextInt();
            if(baptismProbability % 2 == 0){
                p.father.events.add(generateBaptismEvent(p.father.person));
            }
            baptismProbability = new Random().nextInt();
            if(baptismProbability % 2 == 0){
                p.mother.events.add(generateBaptismEvent(p.mother.person));
            }

            //Generates the marriage information for the father and mother. Changes the personId for the mother.
            Event marriage = generateMarriageEvent(p.father.person, p.mother.person);
            p.father.events.add(marriage);
            p.mother.events.add(copyMarriage(marriage,p.mother.person.getPersonID()));

            if(shouldDie(p.father.person)){
                p.father.events.add(generateDeathEvent(p.father.person));
            }
            if(shouldDie(p.mother.person)){
                p.mother.events.add(generateDeathEvent(p.mother.person));
            }


            addGenerationHelper(p.father);
            currentGeneration--;
            addGenerationHelper(p.mother);
            currentGeneration--;
        }

    }

    String getMaleFirstName(){
        int rnd = new Random().nextInt(dg.mData.data.length);
        return dg.mData.data[rnd];
    }

    String getFemaleFirstName(){
        int rnd = new Random().nextInt(dg.fData.data.length);
        return dg.fData.data[rnd];
    }

    String getSurname(){
        int rnd = new Random().nextInt(dg.sData.data.length);
        return dg.sData.data[rnd];
    }

    Location getLocation(){
        int rnd = new Random().nextInt(dg.locData.data.length);
        return dg.locData.data[rnd];
    }

    Event generateBirthEvent(String personID){

        Event birth = new Event();
        int todaysYear = Year.now().getValue();
       // System.out.println(root.person.getBirthYear());
        int userAge;
        if(root.person.getBirthYear() == 0){
            userAge = 25;
        } else {
             userAge = todaysYear - root.person.getBirthYear();
        }
        //System.out.println(currentGeneration);

        int upperBound = 35 * currentGeneration;
        int lowerBound = 19 * currentGeneration;
        int rand = new Random().nextInt(upperBound-lowerBound)+lowerBound;
        int birthYear = todaysYear-userAge-rand;
        birth.setYear(birthYear);
        birth.setDescendant(root.person.getDescendant());
        birth.setEventType("birth");
        birth.setEventID(UUID.randomUUID().toString());
        Location l = getLocation();
        birth.setLatitude(Double.parseDouble(l.getLatitude()));
        birth.setLongitude(Double.parseDouble(l.getLongitude()));
        birth.setCity(l.getCity());
        birth.setCountry(l.getCountry());
        birth.setPersonID(personID);
        return birth;
    }




    @Override
    public String toString(){
        currentGeneration = 0;
        StringBuilder output = new StringBuilder();
        stringHelper(root, output);
        return output.toString();

    }

    public void stringHelper(PersonNode person, StringBuilder output){

        if(person != root) {
            currentGeneration++;
            output.append(person.person.getFirstName());
            output.append(" " + person.person.getLastName());
            output.append(" " + currentGeneration);
            output.append("\n");
            output.append("Father: " +person.person.getFather());
            output.append("\n");
            output.append("Mother: " +person.person.getMother());
            output.append("\n");
            output.append("Spouse: " +person.person.getSpouse());
            output.append("\n");
            output.append("Gender: " +person.person.getGender());
            output.append("\n");
            for (Event e: person.events) {
                output.append(e.getEventType() + ": ");
                output.append(e.getYear() + " ");
                output.append(e.getCity() + ", ");
                output.append(e.getCountry() + " ");
                output.append("\n");
            }
            output.append("\n");

        }
        if(person.father != null){
            stringHelper(person.father, output);
            currentGeneration--;
            stringHelper(person.mother, output);
            currentGeneration--;
        }

    }

    Event generateMarriageEvent(Person person, Person personTwo){
        int birthYear = person.getBirthYear();
        int birthYearTwo = personTwo.getBirthYear();
        Event marriage = new Event();
        //This is for generating random years
        int upperBound = 30;
        int lowerBound = 19;
        int rand = new Random().nextInt(upperBound-lowerBound)+lowerBound;
        int marriageYear = birthYear + rand;
        //Makes sure that the wife was over 18 when the marriage happened.
        if(marriageYear <= birthYearTwo+18){
            marriageYear = marriageYear+(marriageYear - birthYearTwo+18);
        }
        if(marriageYear >= Year.now().getValue()){
            marriageYear -= 20;
        }

        Location l = getLocation();
        marriage.setEventType("marriage");
        marriage.setYear(marriageYear);
        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setDescendant(root.person.getDescendant());
        marriage.setPersonID(person.getPersonID());
        marriage.setLatitude(Double.parseDouble(l.getLatitude()));
        marriage.setLongitude(Double.parseDouble(l.getLongitude()));
        marriage.setCity(l.getCity());
        marriage.setCountry(l.getCountry());
        return marriage;
    }

    Event generateBaptismEvent(Person person){
        int birthYear = person.getBirthYear();
        int upperBound = 10;
        int lowerBound = 0;
        int rand = new Random().nextInt(upperBound-lowerBound)+lowerBound;
        int baptismYear = birthYear + rand;

        Event baptism = new Event();
        Location l = getLocation();
        baptism.setEventType("baptism");
        baptism.setYear(baptismYear);
        baptism.setEventID(UUID.randomUUID().toString());
        baptism.setDescendant(root.person.getDescendant());
        baptism.setPersonID(person.getPersonID());
        baptism.setLatitude(Double.parseDouble(l.getLatitude()));
        baptism.setLongitude(Double.parseDouble(l.getLongitude()));
        baptism.setCity(l.getCity());
        baptism.setCountry(l.getCountry());

        return baptism;
    }

    Event generateDeathEvent(Person person){
        int birthYear = person.getBirthYear();
        int upperBound = 100;
        int lowerBound = 30;
        int rand = new Random().nextInt(upperBound-lowerBound)+lowerBound;
        int deathYear = birthYear + rand;
        int todaysYear = Year.now().getValue();

        if(deathYear > todaysYear){
            deathYear = todaysYear;
        }
        Event death = new Event();
        Location l = getLocation();
        death.setEventType("death");
        death.setYear(deathYear);
        death.setEventID(UUID.randomUUID().toString());
        death.setDescendant(root.person.getDescendant());
        death.setPersonID(person.getPersonID());
        death.setLatitude(Double.parseDouble(l.getLatitude()));
        death.setLongitude(Double.parseDouble(l.getLongitude()));
        death.setCity(l.getCity());
        death.setCountry(l.getCountry());

        return death;
    }


    Boolean shouldDie(Person person){
        int birthYear = person.getBirthYear();
        int upperBound = 100;
        int lowerBound = 30;
        int rand = new Random().nextInt(upperBound-lowerBound)+lowerBound;
        int todaysYear = Year.now().getValue();
        if(birthYear < todaysYear-rand){
            return true;
        } else {
            return false;
        }

    }

    void fillSurnames(PersonNode node){
        String lastName = node.person.getLastName();
        System.out.println(node.person.getFirstName() + " " + node.person.getLastName());
        if(node.father != null){
            node.person.setFather(node.father.person.getPersonID());
            node.father.person.setLastName(lastName);
            fillSurnames(node.father);
        }
        if(node.mother != null){
            node.person.setMother(node.mother.person.getPersonID());
            node.mother.person.setLastName(getSurname());
            fillSurnames(node.mother);
        }
    }

    Event copyMarriage(Event marriage1, String personID){
        Event marriageMother = new Event();

        marriageMother.setPersonID(personID);
        marriageMother.setEventID(UUID.randomUUID().toString());
        marriageMother.setEventType(marriage1.getEventType());
        marriageMother.setCity(marriage1.getCity());
        marriageMother.setCountry(marriage1.getCountry());
        marriageMother.setDescendant(marriage1.getDescendant());
        marriageMother.setLatitude(marriage1.getLatitude());
        marriageMother.setLongitude(marriage1.getLongitude());
        marriageMother.setYear(marriage1.getYear());
        return marriageMother;
    }


}
