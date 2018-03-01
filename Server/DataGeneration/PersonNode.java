package DataGeneration;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;

/**
 * Created by GrantRowberry on 3/6/17.
 */

public class PersonNode {
    Person person = new Person();
    List<Event> events = new ArrayList<>();
    PersonNode father;
    PersonNode mother;
    int generation;

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
